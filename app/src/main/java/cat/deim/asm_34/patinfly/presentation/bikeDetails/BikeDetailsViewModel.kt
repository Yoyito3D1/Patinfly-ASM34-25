package cat.deim.asm_34.patinfly.presentation.bikeDetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.BikeAPIDataSource
import cat.deim.asm_34.patinfly.data.repository.BikeRepository
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.usecase.GetBikeDetailUseCase
import cat.deim.asm_34.patinfly.domain.usecase.ToggleReserveUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class BikeDetailViewModel : ViewModel() {

    companion object { private const val TAG = "BikeDetailVM" }

    private val _bike    = MutableLiveData<Bike?>()
    val   bike : LiveData<Bike?> = _bike

    private val _loading = MutableLiveData(false)
    val   loading: LiveData<Boolean> = _loading

    /** Carga inicial ― sin cambios */
    fun fetchBike(ctx: Context, uuid: String) = viewModelScope.launch {
        val token = SessionManager(ctx).getToken()
        _loading.value = true
        _bike.value = withContext(Dispatchers.IO) {
            BikeRepository(
                BikeAPIDataSource.getInstance(ctx),
                AppDatabase.get(ctx).bikeDao()
            ).let { GetBikeDetailUseCase(it).execute(uuid, token) }
        }
        _loading.value = false
    }

    /** Alterna Reserve / Release usando el nuevo use-case */
    fun toggleReserve(ctx: Context, uuid: String) = viewModelScope.launch {
        val token   = SessionManager(ctx).getToken()
        val session = SessionManager(ctx)
        val repo    = BikeRepository(
            BikeAPIDataSource.getInstance(ctx),
            AppDatabase.get(ctx).bikeDao()
        )
        val uc = ToggleReserveUseCase(repo, session)

        _loading.value = true
        _bike.value    = withContext(Dispatchers.IO) { uc.execute(uuid, token) }
        _loading.value = false
    }
}
