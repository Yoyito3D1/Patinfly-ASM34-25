package cat.deim.asm_34.patinfly.presentation.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.local.SystemPricingPlanDataSource
import cat.deim.asm_34.patinfly.data.datasource.local.UserDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.UserAPIDataSource
import cat.deim.asm_34.patinfly.data.repository.SystemPricingPlanRepository
import cat.deim.asm_34.patinfly.data.repository.UserRepository
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Rent
import cat.deim.asm_34.patinfly.domain.models.User
import cat.deim.asm_34.patinfly.domain.usecase.GetRentHistoryWithBikesUseCase
import cat.deim.asm_34.patinfly.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _history = MutableStateFlow<List<Rent>>(emptyList())
    val history: StateFlow<List<Rent>> = _history

    fun load(ctx: Context, token: String) = viewModelScope.launch {
        _loading.value = true
        try {
            val (u, h) = withContext(Dispatchers.IO) {
                val sessionManager = SessionManager(ctx)
                val userRepo   = UserRepository(
                    UserDataSource.getInstance(ctx),
                    UserAPIDataSource.getInstance(),
                    AppDatabase.get(ctx).userDao(),
                    sessionManager = sessionManager
                )
                val pricingRepo = SystemPricingPlanRepository(
                    SystemPricingPlanDataSource.getInstance(ctx),
                    AppDatabase.get(ctx).systemPricingPlanDao()
                )


                val rents = GetRentHistoryWithBikesUseCase(userRepo, pricingRepo).execute(token)
                val user  = GetUserUseCase(userRepo, sessionManager).execute(token)

                user to rents
            }

            _user.value    = u
            _history.value = h
        } catch (_: Exception) {
            _user.value    = null
            _history.value = emptyList()
        } finally {
            _loading.value = false
        }
    }
}
