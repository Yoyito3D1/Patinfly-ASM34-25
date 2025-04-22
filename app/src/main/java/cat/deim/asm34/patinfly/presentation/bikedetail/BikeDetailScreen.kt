package cat.deim.asm34.patinfly.presentation.bikedetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.deim.asm34.patinfly.R
import cat.deim.asm34.patinfly.domain.models.Bike

@Composable
fun BikeDetailScreen(bike: Bike, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.bike_detail_title, bike.name),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(stringResource(R.string.bike_battery_detail, bike.batteryLevel))
        Text(stringResource(R.string.bike_distance_detail, bike.meters))
        Text(stringResource(R.string.bike_type_detail, bike.bikeType.name))

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text(stringResource(R.string.back_to_main))
        }
    }
}
