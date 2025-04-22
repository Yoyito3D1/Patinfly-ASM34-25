package cat.deim.asm34.patinfly.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm34.patinfly.domain.models.User
import cat.deim.asm34.patinfly.data.session.SessionManager
import cat.deim.asm34.patinfly.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginResult>(LoginResult.Idle)
    val loginState: StateFlow<LoginResult> = _loginState

    private val _loggedUser = MutableStateFlow<User?>(null)
    val loggedUser: StateFlow<User?> = _loggedUser

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = loginUseCase.getUserByEmail(email)
            if (user != null && user.hashedPassword == password) {
                _loggedUser.value = user
                _loginState.value = LoginResult.Success(user)
                sessionManager.saveUserEmail(user.email)
            } else {
                _loginState.value = LoginResult.Error
            }
        }
    }

    fun loadSavedSession() {
        viewModelScope.launch {
            val email = sessionManager.getUserEmail()
            email?.let {
                val user = loginUseCase.getUserByEmail(it)
                if (user != null) {
                    _loggedUser.value = user
                    _loginState.value = LoginResult.Success(user)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _loggedUser.value = null
            _loginState.value = LoginResult.Idle
        }
    }

    sealed class LoginResult {
        object Idle : LoginResult()
        data class Success(val user: User) : LoginResult()
        object Error : LoginResult()
    }
}
