package cat.deim.asm.myapplication.entity

import java.util.*

data class Bike(
    val uuid: UUID,
    val bikeType: String,
    val isAvailable: Boolean,
    val batteryLevel: Int,
    val creationDate: Date
)
