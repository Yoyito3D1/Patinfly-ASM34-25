package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository


class ToggleReserveUseCase(
    private val repo: IBikeRepository,
) {
    suspend fun execute(uuid: String, token: String): Bike {
        val current = session.getReservedBike()

        if (current != null && current != uuid) {
            repo.release(current, token)
            session.saveReservedBike(null)
        }

        val updated = if (current == uuid)
            repo.release(uuid, token)
        else
            repo.reserve(uuid, token)

        session.saveReservedBike(
            if (updated.isReserved) updated.uuid else null
        )
        return updated
    }
}
