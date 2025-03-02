package cat.deim.asm_34.patinfly.domain.repository

import cat.deim.asm_34.patinfly.domain.models.Bike

interface IBikeRepository {
    fun insert(bike: Bike): Boolean
    fun getAll(): Collection<Bike>
    fun getById(uuid: String): Bike?
    fun update(bike: Bike): Boolean
    fun delete(uuid: String): Boolean
}