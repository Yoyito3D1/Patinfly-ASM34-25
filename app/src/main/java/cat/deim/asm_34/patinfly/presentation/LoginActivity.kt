package cat.deim.asm_34.patinfly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import cat.deim.asm.myapplication.datasource.UserDataSource
import cat.deim.asm.myapplication.presentation.UserLoginForm
import cat.deim.asm.myapplication.presentation.theme.MyApplicationTheme
import cat.deim.asm.myapplication.repository.UserRepository
import cat.deim.asm_34.patinfly.domain.usecase.LoginUsecase

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatinflyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserLoginForm(
                        LoginUsecase((UserRepository(
                            UserDataSource.getInstance(
                                LocalContext.current))))
                    )
                }
            }
        }
    }
}