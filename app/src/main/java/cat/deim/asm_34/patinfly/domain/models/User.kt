package cat.deim.asm_34.patinfly.domain.models

import java.util.UUID

data class User(
    val uuid: String,
    val name: String,
    val email: String,
    val hashedPassword: String,
    val creationDate: String,
    val lastConnection: String,
    val deviceId: String,
    val rentalUUID: String?,
    val scooterRented: UUID?,
    val numberOfRents: Int
)