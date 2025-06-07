package cat.deim.asm_34.patinfly.domain.usecase

import android.util.Log
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository

class GetBikeDetailUseCase(
    private val bikeRepository: IBikeRepository
) {
    suspend fun execute(uuid: String, token: String): Bike? {
        Log.d("GetBikeDetailUseCase", "→ execute(uuid=$uuid)")
        val bike = bikeRepository.getById(uuid, token)
        if (bike != null) {
            Log.d("GetBikeDetailUseCase", "   ✅ Found bike: $bike")
        } else {
            Log.d("GetBikeDetailUseCase", "   ⚠️ No bike found for uuid=$uuid")
        }
        return bike
    }
}
