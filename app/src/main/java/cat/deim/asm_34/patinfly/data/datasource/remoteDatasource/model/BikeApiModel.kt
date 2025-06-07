package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model

import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.models.BikeType
import com.google.gson.annotations.SerializedName
import java.util.Date

data class VehicleListApiResponse(
    @SerializedName("vehicles")
    val vehicles: List<BikeApiModel>
)

data class BikeApiModel(
    @SerializedName("name")
    val name: String,

    @SerializedName("is_disabled")
    val isDisabled: Boolean,

    @SerializedName("is_reserved")
    val isReserved: Boolean,

    @SerializedName("is_rented")
    val isRented: Boolean,

    @SerializedName("last_reported")
    val lastReported: String,

    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lon")
    val lon: Double,

    @SerializedName("vehicle_id")
    val uuid: String,

    @SerializedName("vehicle_type_id")
    val vehicleTypeId: String,

    @SerializedName("group_course")
    val groupCourse: String,

    @SerializedName("rental_uris")
    val rentalUris: RentalUris
) {
    fun toDomain(): Bike {
        return Bike(
            uuid               = uuid,
            name               = name,
            bikeType           = BikeType(
                uuid = vehicleTypeId,
                name = vehicleTypeId, // o algún mapping por nombre
                type = "Electric"     // hardcode o extraído de otro campo
            ),
            creationDate       = Date(),
            lastMaintenanceDate = Date(),
            inMaintenance      = false,
            isActive           = !isDisabled,
            batteryLevel       = 100,
            meters             = 0,

            isReserved         = isReserved,
            isRented           = isRented,
            isDisabled         = isDisabled,
            latitude           = lat,
            longitude          = lon,
            rentalUrlAndroid   = rentalUris.android,
            rentalUrlIOS       = rentalUris.ios
        )
    }
}

data class RentalUris(
    @SerializedName("android")
    val android: String,

    @SerializedName("ios")
    val ios: String
)
