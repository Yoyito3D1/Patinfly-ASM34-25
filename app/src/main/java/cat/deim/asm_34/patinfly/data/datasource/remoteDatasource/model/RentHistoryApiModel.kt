package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model

import cat.deim.asm_34.patinfly.domain.models.Rent
import com.google.gson.annotations.SerializedName
import java.time.*
import java.time.format.DateTimeFormatter

data class RentHistoryApiModel(
    val uuid: String,
    val vehicle: BikeApiModel,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time")   val endTime:   String?
) {

    fun toDomain(): Rent = Rent(
        uuid       = uuid,
        vehicleId  = vehicle.uuid,
        start      = parseTimestamp(startTime),
        end        = endTime?.let { parseTimestamp(it) }
    )


    private fun parseTimestamp(ts: String): Instant =
        if (ts.endsWith("Z") || ts.contains('+'))
            Instant.parse(ts)
        else
            LocalDateTime.parse(ts, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .toInstant(ZoneOffset.UTC)
}
