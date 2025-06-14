package cat.deim.asm_34.patinfly.domain.models

import java.time.Instant

data class Rent(
    val uuid      : String,
    val vehicleId : Bike,
    val start     : Instant,
    val end       : Instant?,
    var cost      : Double? = null
)