package cat.deim.asm_34.patinfly.presentation.bikeDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme

class BikeDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uuid = intent.getStringExtra("BIKE_UUID") ?: run { finish(); return }

        val vm = ViewModelProvider(this)[BikeDetailViewModel::class.java]
        vm.fetchBike(applicationContext, uuid)

        setContent {
            PatinflyTheme {
                val bike    by vm.bike.observeAsState()
                val loading by vm.loading.observeAsState(true)

                BikeDetailForm(
                    bike    = bike,
                    loading = loading,
                    onToggleAction = { action ->
                        vm.toggleReserve(applicationContext, uuid, action)
                    }
                )
            }
        }
    }
}
