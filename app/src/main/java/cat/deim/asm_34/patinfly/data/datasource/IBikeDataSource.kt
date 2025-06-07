package cat.deim.asm_34.patinfly.data.datasource

import cat.deim.asm_34.patinfly.data.datasource.local.model.BikeModel

interface IBikeDataSource {
    fun insert(bike: BikeModel): Boolean
    fun getAll(): Collection<BikeModel>
    fun getById(uuid: String): BikeModel?
    fun update(bike: BikeModel): Boolean
    fun delete(uuid: String): Boolean
}
