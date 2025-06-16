package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository

/**
 *  Stub temporal para que la app compile mientras se prueba la lógica de reservas.
 *  ───────────────────────────────────────────────────────────────────────────────
 *  - Devuelve simplemente el estado actual de la bici desde el backend.
 *  - No realiza ninguna operación de start/stop rent.
 *  - Sustituir por la lógica real cuando se implemente.
 */
class ToggleRentUseCase(
    private val bikeRepo: IBikeRepository,
    private val userRepo: IUserRepository     // ← aún no se usa
) {

    suspend fun execute(uuid: String, token: String, isRented: Boolean): Bike {
        // TODO: implementar startRent / stopRent en cuanto se necesite
        // De momento solo devolvemos el estado en vivo para que no pete.
        return bikeRepo.bikeStatus(uuid, token)
    }
}
