package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model

import com.google.gson.annotations.SerializedName


data class RentApiResponse(
    @SerializedName("success")    val success   : Boolean,
    @SerializedName("status")     val status    : String,           // "RENTED" | "VACANT"
    @SerializedName("vehicle")    val vehicle   : BikeApiModel,
    @SerializedName("user")       val user      : UserBriefApiModel,
    @SerializedName("start_time") val startTime : String,
    @SerializedName("end_time")   val endTime   : String? = null    // null en /start
)

data class UserBriefApiModel(
    val uuid     : String,
    val fullname : String,
    val email    : String
)
