package cat.deim.asm.myapplication.entity


import java.util.*

data class SystemPricingPlan(
    val uuid: UUID,
    val planName: String,
    val pricePerMinute: Double,
    val currency: String,
    val isActive: Boolean
)

