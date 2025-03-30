package cat.deim.asm.myapplication.datasource

import cat.deim.asm.myapplication.model.SystemPricingPlanModel
import java.util.UUID

interface ISystemPricingPlanDataSource {
    fun insert(plan: SystemPricingPlanModel): Boolean
    fun getAll(): List<SystemPricingPlanModel>
    fun getById(uuid: UUID): SystemPricingPlanModel?
}