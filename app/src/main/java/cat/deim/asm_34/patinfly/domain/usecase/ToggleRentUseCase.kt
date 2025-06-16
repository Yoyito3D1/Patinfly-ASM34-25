package cat.deim.asm_34.patinfly.domain.usecase

import android.util.Log
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository

/**
 *  Nuevo criterio:
 *  ──────────────────────────────────────────
 *  • El usuario SOLO puede iniciar un alquiler si
 *    previamente tiene la bici reservada (reservedUUID).
 *  • Al iniciar alquiler ➜ reserve() + startRent().
 *  • Al detener alquiler ➜ stopRent() + release().
 *  • Siempre se sincronizan reservedUUID y rentalUUID.
 */
class ToggleRentUseCase(
    private val bikeRepo: IBikeRepository,
    private val userRepo: IUserRepository
) {
    companion object { private const val TAG = "ToggleRentUseCase" }

    suspend fun execute(
        userId: String,
        uuid: String,
        token: String,
        currentlyRented: Boolean
    ): Bike {
        Log.d(TAG, "execute(): user=$userId | bike=$uuid | rented=$currentlyRented")

        return if (currentlyRented) {
            /* ---------- STOP RENT ---------- */
            Log.d(TAG, "→ STOP RENT FLOW")

            Log.d(TAG, "1. stopRent()…")
            bikeRepo.stopRent(uuid, token)

            Log.d(TAG, "2. release()…")
            val bike = bikeRepo.release(uuid, token)

            Log.d(TAG, "3. clear rental/reserved UUIDs")
            userRepo.updateRented(userId,   null)
            userRepo.updateReserved(userId, null)

            Log.d(TAG, "← Rental stopped")
            bike
        } else {
            /* ---------- START RENT ---------- */
            Log.d(TAG, "→ START RENT FLOW")

            Log.d(TAG, "1. ensure bike is reserved")
            bikeRepo.reserve(uuid, token)   // idempotente: si ya estaba OK

            Log.d(TAG, "2. startRent()…")
            val bike = bikeRepo.startRent(uuid, token)

            Log.d(TAG, "3. save UUIDs in user")
            userRepo.updateReserved(userId, uuid)
            userRepo.updateRented(userId,   uuid)

            Log.d(TAG, "← Rental started")
            bike
        }
    }
}
