package cat.deim.asm_34.patinfly.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.UUID
import java.util.Date
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.models.BikeType
import cat.deim.asm_34.patinfly.utils.Converters

@Entity(tableName = "bike")
@TypeConverters(Converters::class)
data class BikeDTO(
    @PrimaryKey val uuid: String,
    val name: String,
    val bikeTypeUuid: String,
    val bikeTypeName: String,
    val bikeTypeType: String,
    val creationDate: Date,
    val lastMaintenanceDate: Date,
    val inMaintenance: Boolean,
    val isActive: Boolean,
    val batteryLevel: Int,
    val meters: Int,
    val isReserved: Boolean,
    val isRented: Boolean,
    val isDisabled: Boolean,
    val latitude: Double,
    val longitude: Double,
    val rentalUrlAndroid: String,
    val rentalUrlIOS: String,
    val reservedBy :String?
) {
    companion object {
        fun fromDomain(bike: Bike) = BikeDTO(
            uuid = bike.uuid,
            name = bike.name,
            bikeTypeUuid = bike.bikeType.uuid,
            bikeTypeName = bike.bikeType.name,
            bikeTypeType = bike.bikeType.type,
            creationDate = bike.creationDate,
            lastMaintenanceDate = bike.lastMaintenanceDate,
            inMaintenance = bike.inMaintenance,
            isActive = bike.isActive,
            batteryLevel = bike.batteryLevel,
            meters = bike.meters,
            isReserved = bike.isReserved,
            isRented = bike.isRented,
            isDisabled = bike.isDisabled,
            latitude = bike.latitude,
            longitude = bike.longitude,
            rentalUrlAndroid = bike.rentalUrlAndroid,
            rentalUrlIOS = bike.rentalUrlIOS,
            reservedBy = bike.reservedBy
        )
    }
}

fun BikeDTO.toDomain() = Bike(
    uuid = uuid,
    name = name,
    bikeType = BikeType(
        uuid = bikeTypeUuid,
        name = bikeTypeName,
        type = bikeTypeType
    ),
    creationDate = creationDate,
    lastMaintenanceDate = lastMaintenanceDate,
    inMaintenance = inMaintenance,
    isActive = isActive,
    batteryLevel = batteryLevel,
    meters = meters,
    isReserved = isReserved,
    isRented = isRented,
    isDisabled = isDisabled,
    latitude = latitude,
    longitude = longitude,
    rentalUrlAndroid = rentalUrlAndroid,
    rentalUrlIOS = rentalUrlIOS,
    reservedBy = reservedBy
)
