package cat.deim.asm_34.patinfly.domain.repository

import cat.deim.asm_34.patinfly.domain.models.SystemPricingPlan

interface ISystemPricingPlanRepository {
    suspend fun insert(plan: SystemPricingPlan): Boolean
    suspend fun getAll(): Collection<SystemPricingPlan>
    suspend fun getById(planId: String): SystemPricingPlan?
    suspend fun update(plan: SystemPricingPlan): Boolean
    suspend fun delete(planId: String): Boolean

}