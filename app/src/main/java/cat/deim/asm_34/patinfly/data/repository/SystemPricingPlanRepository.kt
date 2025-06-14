package cat.deim.asm_34.patinfly.data.repository

import cat.deim.asm_34.patinfly.data.datasource.database.model.SystemPricingPlanDTO
import cat.deim.asm_34.patinfly.data.datasource.database.dbdatasource.SystemPricingPlanDatasource
import cat.deim.asm_34.patinfly.data.datasource.local.SystemPricingPlanDataSource
import cat.deim.asm_34.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm_34.patinfly.domain.repository.ISystemPricingPlanRepository
import java.time.Instant
import kotlin.time.Duration


class SystemPricingPlanRepository(
    private val localSystemPricingPlanDatasource: SystemPricingPlanDataSource,
    private val systemPricingPlanDao: SystemPricingPlanDatasource
) : ISystemPricingPlanRepository {


    override suspend fun getAll(): Collection<SystemPricingPlan> {
        systemPricingPlanDao.getLatest()?.toDomain()?.let {
            return listOf(it)
        }

        val models = localSystemPricingPlanDatasource.getAll()
        val result = mutableListOf<SystemPricingPlan>()

        for (model in models) {
            val domain = model.toDomain()
            systemPricingPlanDao.save(SystemPricingPlanDTO.fromDomain(domain))
            result.add(domain)
        }

        return result
    }


    override suspend fun getById(planId: String): SystemPricingPlan? {
        systemPricingPlanDao.getLatest()?.toDomain()?.takeIf { plan ->
            plan.dataPlans.any { it.planId == planId }
        }?.let { return it }

        val model = localSystemPricingPlanDatasource.getById(planId) ?: return null
        val domain = model.toDomain()

        systemPricingPlanDao.save(SystemPricingPlanDTO.fromDomain(domain))
        return domain
    }


    override suspend fun insert(plan: SystemPricingPlan): Boolean {
        systemPricingPlanDao.save(SystemPricingPlanDTO.fromDomain(plan))
        return true
    }

    override suspend fun update(plan: SystemPricingPlan): Boolean {
        systemPricingPlanDao.update(SystemPricingPlanDTO.fromDomain(plan))
        return true
    }

    override suspend fun delete(planId: String): Boolean {
        val latest = systemPricingPlanDao.getLatest() ?: return false
        if (latest.toDomain().dataPlans.any { it.planId == planId }) {
            systemPricingPlanDao.delete(latest)
            return true
        }
        return false
    }



}