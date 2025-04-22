package cat.deim.asm34.patinfly.presentation.bikelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm34.patinfly.domain.models.Bike
import cat.deim.asm34.patinfly.domain.repository.IBikeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BikeListViewModel(private val repository: IBikeRepository) : ViewModel() {

    private val _bikes = MutableStateFlow<List<Bike>>(emptyList())
    val bikes: StateFlow<List<Bike>> = _bikes

    fun loadBikes() {
        viewModelScope.launch {
            val allBikes = repository.getAllBikes()
            println("🛠️ TOTAL BIKES EN JSON: ${allBikes.size}")
            allBikes.forEach {
                println(" - ${it.name} → active: ${it.isActive}, deleted: ${it.isDeleted}")
            }

            _bikes.value = allBikes.filter { it.isActive && !it.isDeleted }

            println("✅ FILTRADAS: ${_bikes.value.size}")
        }
    }
}
