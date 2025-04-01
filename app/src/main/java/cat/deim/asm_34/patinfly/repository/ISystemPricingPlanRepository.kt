package cat.deim.asm.myapplication.repository

import cat.deim.asm.myapplication.entity.SystemPricingPlan
import java.util.UUID

interface ISystemPricingPlanRepository {
    fun insert(plan: SystemPricingPlan): Boolean
    fun getAll(): List<SystemPricingPlan>
    fun getById(uuid: UUID): SystemPricingPlan?
}