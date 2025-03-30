package cat.deim.asm.myapplication.repository

import cat.deim.asm.myapplication.datasource.IBikeDataSource
import cat.deim.asm.myapplication.entity.Bike
import cat.deim.asm.myapplication.mapper.toDomain
import cat.deim.asm.myapplication.mapper.toModel
import java.util.UUID


class BikeRepository(private val dataSource: IBikeDataSource) : IBikeRepository {

    override fun insert(bike: Bike): Boolean {
        val model = bike.toModel()
        return dataSource.insert(model)
    }

    override fun getAll(): List<Bike> {
        return dataSource.getAll().map { it.toDomain() }
    }

    override fun getById(uuid: UUID): Bike? {
        return dataSource.getById(uuid.toString())
    }

    override fun update(bike: Bike): Boolean {
        val model = bike.toModel()
        return dataSource.update(model)
    }

    override fun delete(uuid: String): Boolean {
        return dataSource.delete(uuid)
    }
}
