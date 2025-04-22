package cat.deim.asm34.patinfly.domain.repository

import cat.deim.asm34.patinfly.data.datasource.model.RawSystemPricingPlan

interface ISystemPricingPlanDataSource {
    fun getPricingPlans(): List<RawSystemPricingPlan>
}
