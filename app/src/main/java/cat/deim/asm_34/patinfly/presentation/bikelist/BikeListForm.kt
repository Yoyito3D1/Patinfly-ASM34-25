package cat.deim.asm_34.patinfly.presentation.bikelist

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.deim.asm_34.patinfly.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeListForm(viewModel: BikeViewModel) {
    val bikes     = viewModel.bikes.observeAsState(emptyList())
    val activity  = LocalContext.current as? Activity

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Patinfly") },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            painterResource(R.drawable.arrow_back),
                            contentDescription = "Back"
                        )
                    }
                },

            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            items(bikes.value) { bike ->
                BikeCardItem(bike)
            }
        }
    }
}
