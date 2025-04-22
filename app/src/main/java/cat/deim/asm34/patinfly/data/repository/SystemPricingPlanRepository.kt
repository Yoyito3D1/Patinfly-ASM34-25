package cat.deim.asm34.patinfly.data.repository

import cat.deim.asm34.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm34.patinfly.domain.repository.ISystemPricingPlanDataSource
import cat.deim.asm34.patinfly.domain.repository.ISystemPricingPlanRepository

class SystemPricingPlanRepository(
    private val dataSource: ISystemPricingPlanDataSource
) : ISystemPricingPlanRepository {

    override fun getAllPlans(): List<SystemPricingPlan> {
        return dataSource.getPricingPlans().map {
            SystemPricingPlan(
                planId = it.plan_id,
                name = it.name.firstOrNull()?.text ?: "Unnamed",
                price = it.price,
                pricePerKm = it.per_km_pricing.firstOrNull()?.rate ?: 0.0,
                pricePerMinute = it.per_min_pricing.firstOrNull()?.rate ?: 0.0,
                description = it.description.firstOrNull()?.text ?: "",
                currency = it.currency,
                isTaxable = it.is_taxable
            )
        }
    }
}
