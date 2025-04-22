package cat.deim.asm34.patinfly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import cat.deim.asm34.patinfly.data.datasource.local.UserLocalDataSource
import cat.deim.asm34.patinfly.data.repository.UserRepository
import cat.deim.asm34.patinfly.presentation.login.LoginViewModel
import cat.deim.asm34.patinfly.presentation.navigation.AppNavHost
import cat.deim.asm34.patinfly.ui.theme.PatinflyASM34Theme
import cat.deim.asm34.patinfly.usecases.LoginUseCase
import cat.deim.asm34.patinfly.data.session.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDataSource = UserLocalDataSource.getInstance(this)
        val userRepository = UserRepository(userDataSource)
        val loginUseCase = LoginUseCase(userRepository)
        val sessionManager = SessionManager(this)
        val loginViewModel = LoginViewModel(loginUseCase, sessionManager)

        setContent {
            PatinflyASM34Theme {
                val navController = rememberNavController()
                AppNavHost(navController = navController, loginViewModel = loginViewModel)
            }
        }
    }
}
