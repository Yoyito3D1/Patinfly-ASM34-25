package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository


class ToggleReserveUseCase(
    private val repo: IBikeRepository,
    private val session: SessionManager
) {
    suspend fun execute(uuid: String, token: String): Bike {
        val current = session.getReservedBike()

        // 1️⃣ Si ya hay otra reserva, la liberamos primero
        if (current != null && current != uuid) {
            repo.release(current, token)          // ignoramos respuesta
            session.saveReservedBike(null)
        }

        // 2️⃣ Alternamos sobre la bici que el usuario pulsó
        val updated = if (current == uuid)
            repo.release(uuid, token)
        else
            repo.reserve(uuid, token)

        // 3️⃣ Guardamos el resultado
        session.saveReservedBike(
            if (updated.isReserved) updated.uuid else null
        )
        return updated
    }
}
