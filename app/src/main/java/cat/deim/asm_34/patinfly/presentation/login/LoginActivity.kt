package cat.deim.asm_34.patinfly.presentation.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.local.UserDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.UserAPIDataSource
import cat.deim.asm_34.patinfly.data.repository.UserRepository
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.usecase.LoginUsecase
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PatinflyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current

                    val loginUsecase = LoginUsecase(
                        UserRepository(
                            localUserDatasource = UserDataSource.getInstance(context),
                            userAPIDataSource = UserAPIDataSource.getInstance(),
                            userDao = AppDatabase.get(context).userDao(),
                            sessionManager = SessionManager(context)
                        )
                    )

                    UserLoginForm(loginUsecase)
                }
            }
        }
    }
}
