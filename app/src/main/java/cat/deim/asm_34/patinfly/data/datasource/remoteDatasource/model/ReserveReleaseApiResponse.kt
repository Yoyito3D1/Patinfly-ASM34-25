package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model

import com.google.gson.annotations.SerializedName

data class ReserveReleaseApiResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("status")  val status : String,
    @SerializedName("vehicle") val vehicle: BikeApiModel
)