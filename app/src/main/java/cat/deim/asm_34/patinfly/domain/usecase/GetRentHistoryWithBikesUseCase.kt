package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm_34.patinfly.domain.models.Rent
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository
import cat.deim.asm_34.patinfly.domain.repository.ISystemPricingPlanRepository
import java.time.Duration

class GetRentHistoryWithBikesUseCase(
    private val userRepo: IUserRepository,
    private val pricingRepo: ISystemPricingPlanRepository
) {
    suspend fun execute(token: String): List<Rent> {
        val rentals = userRepo.getRentHistory(token)
        val plan = pricingRepo.getAll().firstOrNull()?.dataPlans?.firstOrNull() ?: return rentals

        for (rental in rentals) {
            val end = rental.end
            if (end != null) {
                val durationMinutes = Duration.between(rental.start, end).toMinutes()
                val unlockFee = plan.price
                val perMinuteRate = plan.perMinPricing?.rate ?: 0.0
                val total = unlockFee + (durationMinutes * perMinuteRate)
                rental.cost = total
            }
        }

        return rentals
    }
}
