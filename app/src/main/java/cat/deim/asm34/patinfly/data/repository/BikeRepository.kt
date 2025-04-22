package cat.deim.asm34.patinfly.data.repository

import cat.deim.asm34.patinfly.domain.models.Bike
import cat.deim.asm34.patinfly.domain.models.BikeType
import cat.deim.asm34.patinfly.domain.repository.IBikeDataSource
import cat.deim.asm34.patinfly.domain.repository.IBikeRepository

class BikeRepository(private val dataSource: IBikeDataSource) : IBikeRepository {
    override fun getAllBikes(): List<Bike> {
        return dataSource.getAllBikes().map {
            Bike(
                uuid = it.uuid,
                name = it.name,
                bikeType = BikeType(
                    uuid = it.bike_type.uuid,
                    name = it.bike_type.name,
                    type = it.bike_type.type
                ),
                creationDate = it.creation_date,
                lastMaintenanceDate = it.last_maintenance_date,
                inMaintenance = it.in_maintenance,
                isActive = it.is_active,
                isDeleted = it.is_deleted,
                batteryLevel = it.battery_level,
                meters = it.meters
            )
        }
    }
}
