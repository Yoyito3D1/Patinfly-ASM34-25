package cat.deim.asm34.patinfly.data.datasource.local

import android.content.Context
import cat.deim.asm34.patinfly.R
import cat.deim.asm34.patinfly.data.datasource.model.RawSystemPricingPlan
import cat.deim.asm34.patinfly.data.datasource.model.SystemPricingPlanResponse
import cat.deim.asm34.patinfly.domain.repository.ISystemPricingPlanDataSource
import cat.deim.asm34.patinfly.utils.JsonUtils
import com.google.gson.Gson

class SystemPricingPlanLocalDataSource private constructor() : ISystemPricingPlanDataSource {

    private var context: Context? = null
    private var plans: List<RawSystemPricingPlan> = emptyList()

    companion object {
        @Volatile
        private var instance: SystemPricingPlanLocalDataSource? = null

        fun getInstance(context: Context): SystemPricingPlanLocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: SystemPricingPlanLocalDataSource().also { created ->
                    instance = created
                    created.context = context
                    created.loadData()
                }
            }
        }
    }

    private fun loadData() {
        context?.let { ctx ->
            val json = JsonUtils.getJsonDataFromRaw(ctx, R.raw.system_pricing_plans)
            json?.let {
                try {
                    val response = Gson().fromJson(it, SystemPricingPlanResponse::class.java)
                    plans = response.data.plans
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun getPricingPlans(): List<RawSystemPricingPlan> = plans
}
