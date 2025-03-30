package cat.deim.asm.myapplication.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class BikeModel(
    val uuid: UUID,
    @SerializedName("bike_type") val bikeType: String,
    @SerializedName("is_available") val isAvailable: Boolean,
    @SerializedName("battery_level") val batteryLevel: Int,
    @SerializedName("creation_date") val creationDate: Date
)
