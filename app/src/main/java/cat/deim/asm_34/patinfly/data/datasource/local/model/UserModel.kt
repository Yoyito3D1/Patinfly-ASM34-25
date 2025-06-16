package cat.deim.asm_34.patinfly.data.datasource.local.model

import cat.deim.asm_34.patinfly.domain.models.User
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class UserModel(
    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("username")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("hashedPassword")
    val hashed_password: String,

    @SerializedName("creationDate")
    val creation_date: String,

    @SerializedName("lastConnection")
    val last_connection: String,

    @SerializedName("deviceId")
    val device_id: String,

    @SerializedName("isRenting")
    val isRenting: Boolean,

    @SerializedName("scooterRented")
    val scooterRented: String?,

    @SerializedName("numberOfRents")
    val numberOfRents: Int
) {
    fun toDomain(): User {
        return User(
            uuid = uuid,
            name = name,
            email = email,
            hashedPassword = hashed_password,
            creationDate = creation_date,
            lastConnection = last_connection,
            deviceId = device_id,
            scooterRented = scooterRented?.let { UUID.fromString(it) },
            numberOfRents = numberOfRents,
            rentalUUID = null,
            reservedUUID = null,
        )
    }

    companion object {
        fun fromDomain(user: User): UserModel {
            return UserModel(
                uuid = user.uuid,
                name = user.name,
                email = user.email,
                hashed_password = user.hashedPassword,
                creation_date = user.creationDate,
                last_connection = user.lastConnection,
                device_id = user.deviceId,
                isRenting = false,
                scooterRented = user.scooterRented?.toString(),
                numberOfRents = user.numberOfRents
            )
        }
    }
}
