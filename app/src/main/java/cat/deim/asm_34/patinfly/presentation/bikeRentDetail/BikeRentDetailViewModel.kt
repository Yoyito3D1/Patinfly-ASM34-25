package cat.deim.asm_34.patinfly.presentation.bikeRentDetail

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
import cat.deim.asm_34.patinfly.domain.usecase.ToggleRentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BikeRentDetailViewModel : ViewModel() {

    private val _bike = MutableLiveData<Bike?>()
    val bike: LiveData<Bike?> = _bike

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun refresh(ctx: Context, uuid: String, token: String) = viewModelScope.launch {
        _loading.value = true
        _bike.value = withContext(Dispatchers.IO) {
            BikeRepository(
                BikeAPIDataSource.getInstance(),
                AppDatabase.get(ctx).bikeDao()
            ).getById(uuid, token)
        }
        _loading.value = false
    }

    fun toggleRent(ctx: Context, uuid: String, token: String) = viewModelScope.launch {
        val current = _bike.value ?: return@launch
        val userId  = SessionManager(ctx).getUserId() ?: return@launch

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

        _loading.value = true
        _bike.value = withContext(Dispatchers.IO) {
            ToggleRentUseCase(bikeRepo, userRepo)
                .execute(userId, uuid, token, current.isRented)
        }
        _loading.value = false
    }
}
