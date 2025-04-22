package cat.deim.asm34.patinfly.domain.models

data class Bike(
    val uuid: String,
    val name: String,
    val bikeType: BikeType,
    val creationDate: String,
    val lastMaintenanceDate: String?,
    val inMaintenance: Boolean,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val batteryLevel: Int,
    val meters: Int
)