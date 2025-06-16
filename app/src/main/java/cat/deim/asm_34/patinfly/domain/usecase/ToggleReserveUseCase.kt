package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_34.patinfly.presentation.bikeDetails.ToggleAction

class ToggleReserveUseCase(private val repo: IBikeRepository) {

    suspend fun execute(uuid: String, token: String, action: ToggleAction): Bike =
        when (action) {
            ToggleAction.RESERVE -> repo.reserve(uuid, token)
            ToggleAction.RELEASE -> repo.release(uuid, token)
            ToggleAction.NONE    -> repo.bikeStatus(uuid, token)
        }
}
