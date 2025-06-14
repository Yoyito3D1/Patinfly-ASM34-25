package cat.deim.asm_34.patinfly.presentation.bikelist

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.BikeAPIDataSource
import cat.deim.asm_34.patinfly.data.repository.BikeRepository
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.usecase.GetBikesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BikeViewModel : ViewModel() {

    private val _bikes = MutableLiveData<List<Bike>>()
    val bikes: LiveData<List<Bike>> = _bikes

    fun fetchBikes(context: Context) {
        // Obtengo el token directamente del SessionManager
        val token = SessionManager(context).getToken()

        Log.d("BikeViewModel", "→ fetchBikes(), token=$token")

        viewModelScope.launch {
            try {
                val list = withContext(Dispatchers.IO) {
                    val repo = BikeRepository(
                        BikeAPIDataSource.getInstance(),
                        AppDatabase.get(context).bikeDao()
                    )
                    GetBikesUseCase(repo).execute(token)
                }
                Log.d("BikeViewModel", "   🏁 Recibidos ${list.size} elementos")
                _bikes.value = list
            } catch (e: Exception) {
                Log.e("BikeViewModel", "Error fetching bikes: ${e.message}", e)
            }
        }
    }
}
