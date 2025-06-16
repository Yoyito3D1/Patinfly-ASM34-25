package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository
import cat.deim.asm_34.patinfly.presentation.bikeDetails.ToggleAction

class ToggleReserveUseCase(
    private val bikeRepo: IBikeRepository,
    private val userRepo: IUserRepository
) {


    suspend fun execute(
        userId: String,
        uuid:   String,
        token:  String,
        action: ToggleAction
    ): Bike {

        if (action == ToggleAction.RESERVE) {
            val currentReserved = userRepo.getReservedUuid(userId)
            if (currentReserved != null && currentReserved != uuid) {

                val currentRented = userRepo.getRentalUuid(userId)
                if (currentRented != null && currentRented == currentReserved) {
                    bikeRepo.stopRent(currentReserved, token)
                    userRepo.updateRented(userId, null)
                }

                bikeRepo.release(currentReserved, token)
                userRepo.updateReserved(userId, null)
            }
        }

        val bike: Bike = when (action) {
            ToggleAction.RESERVE -> bikeRepo.reserve(uuid, token)
            ToggleAction.RELEASE -> bikeRepo.release(uuid, token)
            ToggleAction.NONE    -> bikeRepo.bikeStatus(uuid, token)
        }

        when (action) {
            ToggleAction.RESERVE -> userRepo.updateReserved(userId, uuid)
            ToggleAction.RELEASE -> userRepo.updateReserved(userId, null)
            ToggleAction.NONE    -> {  }
        }

        return bike
    }
}
