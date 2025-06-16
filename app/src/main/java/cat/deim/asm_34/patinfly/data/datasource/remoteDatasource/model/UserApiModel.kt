package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model


import cat.deim.asm_34.patinfly.domain.models.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable




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
            hashedPassword = "",
            creationDate = "",
            lastConnection = "",
            deviceId = "",
            rentalUUID = null,
            scooterRented = null,
            numberOfRents = 0,
            reservedUUID = null,
        )
    }
}

