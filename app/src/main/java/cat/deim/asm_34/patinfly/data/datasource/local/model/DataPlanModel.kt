package cat.deim.asm_34.patinfly.data.datasource.local.model

import cat.deim.asm_34.patinfly.domain.models.*
import com.google.gson.annotations.SerializedName

data class DataPlanModel(
    @SerializedName("plan_id")
    val planId: String,
    val name: List<LocalizedTextModel>,
    val currency: String,
    val price: Double,
    @SerializedName("is_taxable")
    val isTaxable: Boolean,
    val description: List<LocalizedTextModel>,
    @SerializedName("per_km_pricing")
    val perKmPricing: List<PricingDetailModel>,
    @SerializedName("per_min_pricing")
    val perMinPricing: List<PricingDetailModel>
) {
    fun toDomain(): DataPlan {
        return DataPlan(
            planId = planId,
            name = name.first().toDomain(),
            currency = currency,
            price = price,
            isTaxable = isTaxable,
            description = description.first().toDomain(),
            perKmPricing = perKmPricing.firstOrNull()?.toDomain(),
            perMinPricing = perMinPricing.firstOrNull()?.toDomain()
        )
    }

    companion object {
        fun fromDomain(plan: DataPlan): DataPlanModel {
            return DataPlanModel(
                planId = plan.planId,
                name = listOf(LocalizedTextModel.fromDomain(plan.name)),
                currency = plan.currency,
                price = plan.price,
                isTaxable = plan.isTaxable,
                description = listOf(LocalizedTextModel.fromDomain(plan.description)),
                perKmPricing = plan.perKmPricing?.let { listOf(PricingDetailModel.fromDomain(it)) } ?: emptyList(),
                perMinPricing = plan.perMinPricing?.let { listOf(PricingDetailModel.fromDomain(it)) } ?: emptyList()
            )
        }
    }
}
