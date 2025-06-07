package cat.deim.asm_34.patinfly.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.presentation.login.LoginActivity
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme

class ProfileActivity : ComponentActivity() {

    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = SessionManager(applicationContext)
        val token   = session.getToken()
        Log.d(TAG, "Token='$token', expired=${session.isTokenExpired()}")

        if (token.isBlank() || session.isTokenExpired()) {
            Log.d(TAG, "→ Redirecting to Login (token missing/expired)")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val vm = ViewModelProvider(this)[ProfileViewModel::class.java]
        Log.d(TAG, "Calling fetchProfile()")
        vm.fetchProfile(applicationContext, token)

        setContent {
            PatinflyTheme {
                val loading by vm.loading.observeAsState(true)
                val user    by vm.user.observeAsState()
                val history by vm.history.observeAsState(emptyList())

                Log.d(TAG, "Compose: loading=$loading, user=$user, history=${history.size}")

                when {
                    loading -> CircularProgressIndicator()
                    user != null -> ProfileForm(user!!, history)
                    else -> {
                        Log.d(TAG, "User is null → back to Login")
                        startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}
