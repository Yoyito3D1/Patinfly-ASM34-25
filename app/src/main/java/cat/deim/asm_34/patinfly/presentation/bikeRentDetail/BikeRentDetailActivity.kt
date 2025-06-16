package cat.deim.asm_34.patinfly.presentation.bikeRentDetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.presentation.login.LoginActivity
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme

class BikeRentDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uuid = intent.getStringExtra("BIKE_UUID")
        if (uuid.isNullOrBlank()) { finish(); return }

        val session = SessionManager(applicationContext)
        val token   = session.getToken()
        if (token.isBlank() || session.isTokenExpired()) {
            startActivity(Intent(this, LoginActivity::class.java)); finish(); return
        }

        val vm = ViewModelProvider(this)[BikeRentDetailViewModel::class.java]
        vm.refresh(applicationContext, uuid, token)

        setContent {
            PatinflyTheme {
                val bike    by vm.bike.observeAsState()
                val loading by vm.loading.observeAsState(false)

                BikeRentCard(
                    bike         = bike,
                    loading      = loading,
                    onToggleRent = { vm.toggleRent(applicationContext, uuid, token) }
                )
            }
        }
    }
}
