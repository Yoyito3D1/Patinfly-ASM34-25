package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository


class ToggleRentUseCase(
    private val bikeRepo: IBikeRepository,
    private val userRepo: IUserRepository
) {
    suspend fun execute(uuid: String, token: String, isRented: Boolean): Bike {

        session.getRentedBike()?.let { current ->
            if (current != uuid) {
                repo.stopRent(current, token)
                repo.release(current, token)
            }
        }

        val updated = if (isRented) {
            repo.stopRent(uuid, token).also {
                repo.release(uuid, token)
            }
        } else {
            repo.startRent(uuid, token)
        }

        session.saveRentedBike(if (updated.isRented) updated.uuid else null)
        session.saveReservedBike(null)  // nunca dejamos reserva mientras se alquila

        return updated
    }
}
