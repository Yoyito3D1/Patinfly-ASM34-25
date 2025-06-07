package cat.deim.asm_34.patinfly.presentation.bikelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme

class BikeListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Creamos el viewModel y le pedimos que cargue las bicis
        val viewModel = ViewModelProvider(this)[BikeViewModel::class.java]
        viewModel.fetchBikes(applicationContext)

        setContent {
            PatinflyTheme {
                BikeListForm(viewModel)
            }
        }
    }
}

