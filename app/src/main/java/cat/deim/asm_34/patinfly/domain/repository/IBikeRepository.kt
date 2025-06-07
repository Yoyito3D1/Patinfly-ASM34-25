package cat.deim.asm_34.patinfly.domain.repository

import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.models.ServerStatus

interface IBikeRepository {
    suspend fun insert(bike: Bike): Boolean

    suspend fun getAll(token: String): Collection<Bike>

    suspend fun getById(uuid: String, token: String): Bike?

    suspend fun update(bike: Bike): Boolean

    suspend fun delete(uuid: String): Boolean

    suspend fun status(): ServerStatus

    suspend fun reserve(uuid: String, token: String): Bike
    suspend fun release(uuid: String, token: String): Bike
    suspend fun startRent(uuid: String, token: String): Bike
    suspend fun stopRent(uuid: String, token: String): Bike
}
