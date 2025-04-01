package cat.deim.asm.myapplication.repository

import cat.deim.asm.myapplication.datasource.ISystemPricingPlanDataSource
import cat.deim.asm.myapplication.entity.SystemPricingPlan
import cat.deim.asm.myapplication.mapper.toDomain
import cat.deim.asm.myapplication.mapper.toModel
import java.util.*

class SystemPricingPlanRepository(private val dataSource: ISystemPricingPlanDataSource) : ISystemPricingPlanRepository {

    override fun insert(plan: SystemPricingPlan): Boolean {
        return dataSource.insert(plan.toModel())
    }

    override fun getAll(): List<SystemPricingPlan> {
        return dataSource.getAll().map { it.toDomain() }
    }

    override fun getById(uuid: UUID): SystemPricingPlan? {
        return dataSource.getById(uuid)?.toDomain()
    }
}
