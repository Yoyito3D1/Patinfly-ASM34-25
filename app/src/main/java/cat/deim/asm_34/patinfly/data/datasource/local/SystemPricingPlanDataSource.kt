package cat.deim.asm_34.patinfly.data.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import cat.deim.asm_34.patinfly.data.datasource.ISystemPricingPlanDataSource
import cat.deim.asm_34.patinfly.data.datasource.local.model.SystemPricingPlanModel
import cat.deim.asm_34.patinfly.data.datasource.local.model.PricingDataModel
import cat.deim.asm_34.patinfly.data.datasource.local.model.DataPlanModel
import com.google.gson.GsonBuilder

class SystemPricingPlanDataSource private constructor() : ISystemPricingPlanDataSource {

    private var context: Context? = null
    private val plans: MutableMap<String, DataPlanModel> = mutableMapOf()
    private lateinit var wrapper: SystemPricingPlanModel

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: SystemPricingPlanDataSource? = null

        fun getInstance(context: Context): SystemPricingPlanDataSource =
            instance ?: synchronized(this) {
                instance ?: SystemPricingPlanDataSource().also {
                    it.context = context
                    it.loadPricingData()
                    instance = it
                }
            }
    }

    private fun loadPricingData() {
        context?.let {
            val json = AssetsProvider.getJsonDataFromRawAsset(it, "system_pricing_plans")
            json?.let { data ->
                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
                    .create()
                wrapper = gson.fromJson(data, SystemPricingPlanModel::class.java)
                wrapper.data.plans.forEach { plan -> plans[plan.planId] = plan }
            }
        }
    }

    override fun insert(plan: SystemPricingPlanModel): Boolean {
        val dataPlan = plan.data.plans.firstOrNull() ?: return false
        if (plans.containsKey(dataPlan.planId)) return false
        plans[dataPlan.planId] = dataPlan
        return true
    }

    override fun getAll(): Collection<SystemPricingPlanModel> {
        return listOf(
            SystemPricingPlanModel(
                lastUpdated = wrapper.lastUpdated,
                ttl = wrapper.ttl,
                version = wrapper.version,
                data = PricingDataModel(plans.values.toList())
            )
        )
    }

    override fun getById(id: String): SystemPricingPlanModel? {
        val plan = plans[id] ?: return null
        return SystemPricingPlanModel(
            lastUpdated = wrapper.lastUpdated,
            ttl = wrapper.ttl,
            version = wrapper.version,
            data = PricingDataModel(listOf(plan))
        )
    }

    override fun update(plan: SystemPricingPlanModel): Boolean {
        val dataPlan = plan.data.plans.firstOrNull() ?: return false
        if (!plans.containsKey(dataPlan.planId)) return false
        plans[dataPlan.planId] = dataPlan
        return true
    }

    override fun delete(id: String): Boolean {
        return plans.remove(id) != null
    }
}
