package cat.deim.asm.myapplication.datasource

import android.annotation.SuppressLint
import android.content.Context
import cat.deim.asm.myapplication.model.SystemPricingPlanModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.util.*

class SystemPricingPlanDataSource private constructor() : ISystemPricingPlanDataSource {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: SystemPricingPlanDataSource? = null

        fun getInstance(context: Context): SystemPricingPlanDataSource =
            instance ?: synchronized(this) {
                instance ?: SystemPricingPlanDataSource().also {
                    it.context = context
                    it.loadData()
                    instance = it
                }
            }
    }

    private var context: Context? = null
    private val plans: MutableMap<UUID, SystemPricingPlanModel> = HashMap()

    private fun loadData() {
        context?.let { ctx ->
            val json = AssetsProvider.getJsonDataFromRawAsset(ctx, "system_pricing_plan")
            if (json != null) {
                val plan = parseJson(json)
                if (plan != null) {
                    insert(plan)
                }
            }
        }
    }

    private fun parseJson(json: String): SystemPricingPlanModel? {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").create()
        return try {
            gson.fromJson(json, SystemPricingPlanModel::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    override fun insert(plan: SystemPricingPlanModel): Boolean {
        plans[plan.uuid] = plan
        return true
    }

    override fun getAll(): List<SystemPricingPlanModel> {
        return plans.values.toList()
    }

    override fun getById(uuid: UUID): SystemPricingPlanModel? {
        return plans[uuid]
    }
}
