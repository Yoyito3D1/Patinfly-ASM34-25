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

        val uuid = intent.getStringExtra("BIKE_UUID")
        if (uuid.isNullOrEmpty()) { finish(); return }

        val viewModel = ViewModelProvider(this)[BikeDetailViewModel::class.java]
        viewModel.fetchBike(applicationContext, uuid)

        setContent {
            PatinflyTheme {
                val bike    by viewModel.bike.observeAsState()
                val loading by viewModel.loading.observeAsState(true)

                BikeDetailForm(
                    bike    = bike,
                    loading = loading,
                    onToggleReserve = {
                        viewModel.toggleReserve(applicationContext, uuid)
                    }
                )
            }
        }

    }
}

