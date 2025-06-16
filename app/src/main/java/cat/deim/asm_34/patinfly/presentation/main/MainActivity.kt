package cat.deim.asm_34.patinfly.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.local.UserDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.BikeAPIDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.UserAPIDataSource
import cat.deim.asm_34.patinfly.data.repository.BikeRepository
import cat.deim.asm_34.patinfly.data.repository.UserRepository
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.usecase.*
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme

class MainActivity : ComponentActivity() {

    /* contador que forzará el refresco del composable al volver a RESUME */
    private var refreshTrigger by mutableIntStateOf(0)

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
            BikeAPIDataSource.getInstance(),
            AppDatabase.get(applicationContext).bikeDao()
        )

        setContent {
            PatinflyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = MaterialTheme.colorScheme.background
                ) {
                    MainScreenContent(
                        getUserUseCase          = GetUserUseCase(userRepo, session),
                        getBikesUseCase         = GetBikesUseCase(bikeRepo),
                        getUserReservedUseCase  = GetUserReservedUsecase(userRepo),
                        refreshTrigger          = refreshTrigger            // ← nuevo
                    )
                }
            }
        }
    }

    /** Cada vez que volvemos desde otra pantalla */
    override fun onResume() {
        super.onResume()
        refreshTrigger++            // fuerza un nuevo LaunchedEffect
    }
}
