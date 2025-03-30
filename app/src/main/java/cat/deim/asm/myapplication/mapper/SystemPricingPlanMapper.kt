package cat.deim.asm.myapplication.mapper

import cat.deim.asm.myapplication.entity.SystemPricingPlan
import cat.deim.asm.myapplication.model.SystemPricingPlanModel

fun SystemPricingPlanModel.toDomain(): SystemPricingPlan {
    return SystemPricingPlan(uuid, planName, pricePerMinute, currency, isActive)
}

fun SystemPricingPlan.toModel(): SystemPricingPlanModel {
    return SystemPricingPlanModel(uuid, planName, pricePerMinute, currency, isActive)
}
