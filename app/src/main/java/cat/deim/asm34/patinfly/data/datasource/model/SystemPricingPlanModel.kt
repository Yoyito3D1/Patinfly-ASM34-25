package cat.deim.asm34.patinfly.data.datasource.model

data class SystemPricingPlanResponse(
    val last_updated: String,
    val ttl: Int,
    val version: String,
    val data: PricingData
)

data class PricingData(
    val plans: List<RawSystemPricingPlan>
)

data class RawSystemPricingPlan(
    val plan_id: String,
    val name: List<LocalizedText>,
    val currency: String,
    val price: Double,
    val is_taxable: Boolean,
    val description: List<LocalizedText>,
    val per_km_pricing: List<PricingDetail>,
    val per_min_pricing: List<PricingDetail>
)

data class LocalizedText(
    val text: String,
    val language: String
)

data class PricingDetail(
    val start: Int,
    val rate: Double,
    val interval: Int
)
