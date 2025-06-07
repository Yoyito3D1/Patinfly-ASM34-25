package cat.deim.asm_34.patinfly.data.datasource.local.model

import cat.deim.asm_34.patinfly.domain.models.Pricing

data class PricingDetailModel(
    val start: Int,
    val rate: Double,
    val interval: Int
) {
    fun toDomain(): Pricing {
        return Pricing(start = start, rate = rate, interval = interval)
    }

    companion object {
        fun fromDomain(pricing: Pricing): PricingDetailModel {
            return PricingDetailModel(
                start = pricing.start,
                rate = pricing.rate,
                interval = pricing.interval
            )
        }
    }
}
