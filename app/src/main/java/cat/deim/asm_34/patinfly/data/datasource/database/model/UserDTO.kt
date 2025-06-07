package cat.deim.asm_34.patinfly.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import cat.deim.asm_34.patinfly.domain.models.User
import java.util.UUID

@Entity(tableName = "user")
data class UserDTO(
    @PrimaryKey val uuid: String,
    val username: String,
    val email: String,
    val hashedPassword: String,
    val creationDate: String,
    val lastConnection: String,
    val deviceId: String,
    val isRenting: Boolean,
    val scooterRented: UUID?,
    val numberOfRents: Int
) {
    companion object {
        fun fromDomain(user: User): UserDTO {
            return UserDTO(
                uuid = user.uuid,
                username = user.name,
                email = user.email,
                hashedPassword = user.hashedPassword,
                creationDate = user.creationDate,
                lastConnection = user.lastConnection,
                deviceId = user.deviceId,
                isRenting = user.isRenting,
                scooterRented = user.scooterRented,
                numberOfRents = user.numberOfRents
            )
        }
    }
}

fun UserDTO.toDomain(): User {
    return User(
        uuid = uuid,
        name = username,
        email = email,
        hashedPassword = hashedPassword,
        creationDate = creationDate,
        lastConnection = lastConnection,
        deviceId = deviceId,
        isRenting = isRenting,
        scooterRented = scooterRented,
        numberOfRents = numberOfRents
    )
}
