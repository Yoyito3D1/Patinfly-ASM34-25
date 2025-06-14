package cat.deim.asm_34.patinfly.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.presentation.login.LoginActivity
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme

class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = SessionManager(applicationContext)
        val token   = session.getToken()
        if (token.isBlank() || session.isTokenExpired()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish(); return
        }

        val vm = ViewModelProvider(this)[ProfileViewModel::class.java]
        vm.load(applicationContext, token)

        setContent {
            PatinflyTheme {
                val loading by vm.loading.collectAsState()
                val user    by vm.user.collectAsState()
                val history by vm.history.collectAsState()

                when {
                    loading          -> CircularProgressIndicator()
                    user != null     -> ProfileForm(user!!, history)
                    else             -> {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}
