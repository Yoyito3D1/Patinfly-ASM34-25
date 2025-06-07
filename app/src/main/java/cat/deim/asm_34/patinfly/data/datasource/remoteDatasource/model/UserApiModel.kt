package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model


import cat.deim.asm_34.patinfly.domain.models.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


import java.util.*

@Serializable
data class UserApiModel(
    @SerialName("uuid")
    val uuid: String,

    @SerialName("fullname")
    val fullname: String,

    @SerialName("email")
    val email: String,

    @SerialName("subject")
    val subject: String
) {
    fun toDomain(): User {
        return User(
            uuid = uuid,
            name = fullname,
            email = email,
            hashedPassword = "",                // No disponible en API
            creationDate = "",                 // No disponible en API
            lastConnection = "",              // No disponible en API
            deviceId = "",                    // No disponible en API
            isRenting = false,                // No disponible en API
            scooterRented = null,             // No disponible en API
            numberOfRents = 0                 // No disponible en API
        )
    }
}

