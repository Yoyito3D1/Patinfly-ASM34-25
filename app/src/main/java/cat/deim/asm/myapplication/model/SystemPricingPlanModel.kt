package cat.deim.asm.myapplication.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class SystemPricingPlanModel(
    val uuid: UUID,
    @SerializedName("plan_name") val planName: String,
    @SerializedName("price_per_minute") val pricePerMinute: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("is_active") val isActive: Boolean
)