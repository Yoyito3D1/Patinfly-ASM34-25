package cat.deim.asm_34.patinfly.domain.usecase

import android.util.Log
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository

class GetBikesUseCase(private val bikeRepository: IBikeRepository) {
    suspend fun execute(token: String): List<Bike> {
        Log.d("GetBikesUseCase", "→ execute()")
        val list = bikeRepository.getAll(token).toList()
        Log.d("GetBikesUseCase", "   ✅ Devueltos ${list.size} elementos")
        return list
    }
}
