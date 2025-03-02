package cat.deim.asm_34.patinfly.domain.models

import java.time.LocalDateTime
import java.util.UUID

data class Bike(
    val uuid: UUID = UUID.randomUUID(),
    var name: String,
    var bikeType: BikeType,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    var lastMaintenanceDate: LocalDateTime? = null,
    var inMaintenance: Boolean = false,
    var isActive: Boolean = true,
    var batteryLevel: Int = 100,
    var meters: Double = 0.0
)
