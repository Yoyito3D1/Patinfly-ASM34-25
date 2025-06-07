package cat.deim.asm_34.patinfly.presentation.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.local.UserDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.BikeAPIDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.UserAPIDataSource
import cat.deim.asm_34.patinfly.data.repository.BikeRepository
import cat.deim.asm_34.patinfly.data.repository.UserRepository
import cat.deim.asm_34.patinfly.domain.models.RentWithBike
import cat.deim.asm_34.patinfly.domain.models.User
import cat.deim.asm_34.patinfly.domain.usecase.GetRentHistoryWithBikesUseCase
import cat.deim.asm_34.patinfly.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {

    companion object { private const val TAG = "ProfileVM" }

    private val _loading = MutableLiveData(true)
    val   loading: LiveData<Boolean> = _loading

    private val _user = MutableLiveData<User?>()
    val   user : LiveData<User?> = _user

    private val _history = MutableLiveData<List<RentWithBike>>(emptyList())
    val   history: LiveData<List<RentWithBike>> = _history

    fun fetchProfile(ctx: Context, token: String) = viewModelScope.launch {
        Log.d(TAG, "fetchProfile() start, token.len=${token.length}")
        _loading.value = true

        try {
            val (u, h) = withContext(Dispatchers.IO) {

                val userRepo = UserRepository(
                    localUserDatasource = UserDataSource.getInstance(ctx),
                    userAPIDataSource   = UserAPIDataSource.getInstance(),
                    userDao             = AppDatabase.get(ctx).userDao(),
                    sessionManager      = null
                )
                val bikeRepo = BikeRepository(
                    BikeAPIDataSource.getInstance(ctx),
                    AppDatabase.get(ctx).bikeDao()
                )

                val user  = GetUserUseCase(userRepo).execute(token).also {
                    Log.d(TAG, "User loaded: $it")
                }
                val rents = GetRentHistoryWithBikesUseCase(userRepo, bikeRepo)
                    .execute(token).also {
                        Log.d(TAG, "Rent history size=${it.size}")
                    }

                user to rents
            }

            _user.value    = u
            _history.value = h

        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}", e)
            _user.value    = null
            _history.value = emptyList()
        } finally {
            _loading.value = false
            Log.d(TAG, "fetchProfile() end")
        }
    }
}
