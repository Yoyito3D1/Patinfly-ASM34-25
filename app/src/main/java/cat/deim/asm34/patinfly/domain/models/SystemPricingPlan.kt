package cat.deim.asm34.patinfly.domain.models


data class SystemPricingPlan(
    val planId: String,
    val name: String,
    val price: Double,
    val pricePerKm: Double,
    val pricePerMinute: Double,
    val description: String,
    val currency: String,
    val isTaxable: Boolean
)
