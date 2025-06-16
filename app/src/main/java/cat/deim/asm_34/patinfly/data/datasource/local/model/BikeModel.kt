package cat.deim.asm_34.patinfly.data.datasource.local.model

import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.models.BikeType
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class BikeModel(
    val uuid: String,
    val name: String,
    @SerializedName("bike_type")
    val bikeType: BikeTypeModel,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("last_maintenance_date")
    val lastMaintenanceDate: String?,
    @SerializedName("in_maintenance")
    val inMaintenance: Boolean,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_deleted")
    val isDeleted: Boolean,
    @SerializedName("battery_level")
    val batteryLevel: Int,
    val meters: Int
) {
    fun toDomain(): Bike {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return Bike(
            uuid = uuid,
            name = name,
            bikeType = bikeType.toDomain(),
            creationDate = formatter.parse(creationDate) ?: Date(),
            lastMaintenanceDate = lastMaintenanceDate?.let {
                formatter.parse(it)
            } ?: Date(),
            inMaintenance = inMaintenance,
            isActive = isActive,
            batteryLevel = batteryLevel,
            meters = meters,
            isReserved = false,
            isRented = false,
            isDisabled = false,
            latitude = 0.0,
            longitude = 0.0,
            rentalUrlAndroid = "",
            rentalUrlIOS = "",
            reservedBy = null,
        )
    }

    companion object {
        fun fromDomain(bike: Bike): BikeModel {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            return BikeModel(
                uuid = bike.uuid,
                name = bike.name,
                bikeType = BikeTypeModel.fromDomain(bike.bikeType),
                creationDate = formatter.format(bike.creationDate),
                lastMaintenanceDate = formatter.format(bike.lastMaintenanceDate),
                inMaintenance = bike.inMaintenance,
                isActive = bike.isActive,
                isDeleted = false,
                batteryLevel = bike.batteryLevel,
                meters = bike.meters
            )
        }
    }
}
