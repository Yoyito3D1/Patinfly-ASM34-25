package cat.deim.asm_34.patinfly.presentation.bikeDetails

import android.content.Context
import androidx.lifecycle.*
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.local.UserDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.BikeAPIDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.UserAPIDataSource
import cat.deim.asm_34.patinfly.data.repository.BikeRepository
import cat.deim.asm_34.patinfly.data.repository.UserRepository
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.usecase.GetBikeDetailUseCase
import cat.deim.asm_34.patinfly.domain.usecase.ToggleReserveUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BikeDetailViewModel : ViewModel() {

    private val _bike = MutableLiveData<Bike?>()
    val bike: LiveData<Bike?> = _bike

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun fetchBike(ctx: Context, uuid: String) = viewModelScope.launch {
        val token = SessionManager(ctx).getToken()
        _loading.value = true
        _bike.value = withContext(Dispatchers.IO) {
            val repo = BikeRepository(
                BikeAPIDataSource.getInstance(),
                AppDatabase.get(ctx).bikeDao()
            )
            GetBikeDetailUseCase(repo).execute(uuid, token)
        }
        _loading.value = false
    }

    fun toggleReserve(ctx: Context, uuid: String, action: ToggleAction) = viewModelScope.launch {
        if (action == ToggleAction.NONE) return@launch

        val token   = SessionManager(ctx).getToken()
        val userId  = SessionManager(ctx).getUserId() ?: return@launch   // no user → nada

        val bikeRepo = BikeRepository(
            BikeAPIDataSource.getInstance(),
            AppDatabase.get(ctx).bikeDao()
        )
        val userRepo = UserRepository(
            UserDataSource.getInstance(ctx),
            UserAPIDataSource.getInstance(),
            AppDatabase.get(ctx).userDao(),
            null
        )
        val uc = ToggleReserveUseCase(bikeRepo, userRepo)

        _loading.value = true
        _bike.value = withContext(Dispatchers.IO) { uc.execute(userId, uuid, token, action) }
        _loading.value = false
    }
}
