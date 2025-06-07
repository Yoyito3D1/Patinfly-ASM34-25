package cat.deim.asm_34.patinfly.domain.usecase


import android.util.Log
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.models.Rent
import cat.deim.asm_34.patinfly.domain.models.RentWithBike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository
import kotlinx.coroutines.*
class GetRentHistoryWithBikesUseCase(
    private val userRepo: IUserRepository,
    private val bikeRepo: IBikeRepository
) {
    private val TAG = "RentHistUC"

    suspend fun execute(token: String): List<RentWithBike> = coroutineScope {
        Log.d(TAG, "execute()")
        val rents = userRepo.getRentHistory(token)
        Log.d(TAG, "Base rents=${rents.size}")

        rents.map { rent ->
            async(Dispatchers.IO) {
                val bike = runCatching {
                    bikeRepo.getById(rent.vehicleId, token).also {
                        Log.d(TAG, "Bike resolved id=${rent.vehicleId} → $it")
                    }
                }.getOrElse {
                    Log.w(TAG, "Bike not found id=${rent.vehicleId}")
                    null
                }
                RentWithBike(rent, bike)
            }
        }.awaitAll().also {
            Log.d(TAG, "Mapped list size=${it.size}")
        }
    }
}

