package cat.deim.asm.myapplication.datasource

import cat.deim.asm.myapplication.entity.Bike
import cat.deim.asm.myapplication.model.BikeModel

interface IBikeDataSource {
    fun insert(bike: BikeModel): Boolean
    fun insertOrUpdate(bike: BikeModel): Boolean
    fun getAll(): Collection<BikeModel>
    fun getActiveAll(): Collection<BikeModel>
    fun getById(uuid: String): Bike?
    fun update(bike: BikeModel): Boolean
    fun delete(uuid: String): Boolean
    fun deleteAll(): Boolean
}
