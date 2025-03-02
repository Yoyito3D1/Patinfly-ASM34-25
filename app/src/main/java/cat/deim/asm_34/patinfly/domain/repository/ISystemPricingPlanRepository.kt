package cat.deim.asm_34.patinfly.domain.repository

import cat.deim.asm_34.patinfly.domain.models.Bike


interface ISystemPricingPlanRepository {
    fun insert(systemPricingPlan: SystemPricingPlan): Boolean
    fun getAll(): Collection<SystemPricingPlan>
    fun getById(planId: String): SystemPricingPlan?
    fun update(systemPricingPlan: SystemPricingPlan): Boolean
    fun delete(planId: String): Boolean
}