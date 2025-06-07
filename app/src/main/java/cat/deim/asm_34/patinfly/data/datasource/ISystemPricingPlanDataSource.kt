package cat.deim.asm_34.patinfly.data.datasource

import cat.deim.asm_34.patinfly.data.datasource.local.model.SystemPricingPlanModel

interface ISystemPricingPlanDataSource {
    fun insert(plan: SystemPricingPlanModel): Boolean
    fun getAll(): Collection<SystemPricingPlanModel>
    fun getById(id: String): SystemPricingPlanModel?
    fun update(plan: SystemPricingPlanModel): Boolean
    fun delete(id: String): Boolean
}