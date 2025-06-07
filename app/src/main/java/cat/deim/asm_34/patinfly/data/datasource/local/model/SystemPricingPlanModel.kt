package cat.deim.asm_34.patinfly.data.datasource.local.model

import cat.deim.asm_34.patinfly.domain.models.*
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class SystemPricingPlanModel(
    @SerializedName("last_updated")
    val lastUpdated: String,
    val ttl: Int,
    val version: String,
    val data: PricingDataModel
) {
    fun toDomain(): SystemPricingPlan {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        return SystemPricingPlan(
            lastUpdated = formatter.parse(lastUpdated) ?: Date(),
            ttl = ttl,
            version = version,
            dataPlans = data.plans.map { it.toDomain() }
        )
    }

    companion object {
        fun fromDomain(plan: SystemPricingPlan): SystemPricingPlanModel {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
            return SystemPricingPlanModel(
                lastUpdated = formatter.format(plan.lastUpdated),
                ttl = plan.ttl,
                version = plan.version,
                data = PricingDataModel(
                    plans = plan.dataPlans.map { DataPlanModel.fromDomain(it) }
                )
            )
        }
    }
}
