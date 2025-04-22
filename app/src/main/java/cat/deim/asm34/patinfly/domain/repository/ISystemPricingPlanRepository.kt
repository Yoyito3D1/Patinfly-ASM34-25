package cat.deim.asm34.patinfly.domain.repository

import cat.deim.asm34.patinfly.domain.models.SystemPricingPlan

interface ISystemPricingPlanRepository {
    fun getAllPlans(): List<SystemPricingPlan>
}
