package cat.deim.asm_34.patinfly.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.local.UserDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.BikeAPIDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.UserAPIDataSource
import cat.deim.asm_34.patinfly.data.repository.BikeRepository
import cat.deim.asm_34.patinfly.data.repository.UserRepository
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.usecase.GetBikesUseCase
import cat.deim.asm_34.patinfly.domain.usecase.GetUserUseCase
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = SessionManager(applicationContext)
        val token   = session.getToken()
        Log.d("MainActivity", "Token='$token', expired? ${session.isTokenExpired()}")

        val userRepo = UserRepository(
            localUserDatasource = UserDataSource.getInstance(applicationContext),
            userAPIDataSource   = UserAPIDataSource.getInstance(),
            userDao             = AppDatabase.get(applicationContext).userDao(),
            sessionManager      = session
        )
        val bikeRepo = BikeRepository(
            BikeAPIDataSource.getInstance(applicationContext),
            AppDatabase.get(applicationContext).bikeDao()
        )

        setContent {
            PatinflyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = MaterialTheme.colorScheme.background
                ) {
                    MainScreenContent(
                        getUserUseCase  = GetUserUseCase(userRepo),
                        getBikesUseCase = GetBikesUseCase(bikeRepo)
                    )
                }
            }
        }
    }
}
