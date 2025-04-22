package cat.deim.asm34.patinfly.presentation.pricing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm34.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm34.patinfly.domain.repository.ISystemPricingPlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PricingViewModel(private val repository: ISystemPricingPlanRepository) : ViewModel() {

    private val _plans = MutableStateFlow<List<SystemPricingPlan>>(emptyList())
    val plans: StateFlow<List<SystemPricingPlan>> = _plans

    fun loadPlans() {
        viewModelScope.launch {
            _plans.value = repository.getAllPlans()
        }
    }
}
