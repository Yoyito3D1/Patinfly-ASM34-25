package cat.deim.asm.myapplication.model

import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.UUID

data class UserModel(
    val uuid: UUID,
    val username: String,
    val email: String,
    @SerializedName("is_renting") val isRenting: Boolean,
    @SerializedName("scooter_rented") val scooterRented: UUID?,
    @SerializedName("creation_date") val creationDate: Date,
    @SerializedName("number_of_rents") val numberOfRents: Int
)