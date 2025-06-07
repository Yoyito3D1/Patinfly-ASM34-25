package cat.deim.asm_34.patinfly.domain.models

import java.util.Date


data class SystemPricingPlan(
    val lastUpdated: Date,
    val ttl: Int,
    val version: String,
    val dataPlans: List<DataPlan>
)

data class DataPlan(
    val planId: String,
    val name: LocalizedText,
    val currency: String,
    val price: Double,
    val isTaxable: Boolean,
    val description: LocalizedText,
    val perKmPricing: Pricing?,
    val perMinPricing: Pricing?
)

data class LocalizedText(
    val text: String,
    val language: String
)

data class Pricing(
    val start: Int,
    val rate: Double,
    val interval: Int
)