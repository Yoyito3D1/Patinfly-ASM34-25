package cat.deim.asm.myapplication.repository

import cat.deim.asm.myapplication.entity.Bike
import java.util.UUID

interface IBikeRepository {
    fun insert(bike: Bike): Boolean
    fun getAll(): List<Bike>
    fun getById(id: UUID): Bike?
    fun update(bike: Bike): Boolean
    fun delete(id: String): Boolean
}
