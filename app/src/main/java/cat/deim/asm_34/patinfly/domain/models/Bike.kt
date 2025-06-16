package cat.deim.asm_34.patinfly.domain.models

import java.util.Date

data class Bike(
    val uuid: String,
    val name: String,
    val bikeType: BikeType,
    val creationDate: Date,
    val lastMaintenanceDate: Date,
    val inMaintenance: Boolean,
    val isActive: Boolean,
    val batteryLevel: Int,
    val meters: Int,
    val reservedBy: String?,
    val isReserved: Boolean,
    val isRented: Boolean,
    val isDisabled: Boolean,
    val latitude: Double,
    val longitude: Double,
    val rentalUrlAndroid: String,
    val rentalUrlIOS: String
)
