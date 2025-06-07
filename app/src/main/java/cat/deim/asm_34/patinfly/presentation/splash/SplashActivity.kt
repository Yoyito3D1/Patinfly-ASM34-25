package cat.deim.asm_34.patinfly.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import cat.deim.asm_34.patinfly.presentation.login.LoginActivity
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatinflyTheme {
                val context = LocalContext.current
                SplashForm {
                    startActivity(Intent(context, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}
