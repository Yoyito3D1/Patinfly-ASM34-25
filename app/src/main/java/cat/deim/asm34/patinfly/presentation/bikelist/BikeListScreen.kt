package cat.deim.asm34.patinfly.presentation.bikelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.deim.asm34.patinfly.R
import cat.deim.asm34.patinfly.domain.models.Bike
import kotlin.random.Random

@Composable
fun BikeListScreen(
    viewModel: BikeListViewModel,
    onViewDetails: (String) -> Unit
) {
    val bikes by viewModel.bikes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadBikes()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(stringResource(R.string.available_bikes), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(bikes) { bike ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.bike_sample),
                            contentDescription = stringResource(R.string.bike_image_description),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = bike.bikeType.name, style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(getBatteryLabelRes(bike.batteryLevel)),
                            style = MaterialTheme.typography.bodySmall,
                            color = when {
                                bike.batteryLevel >= 75 -> MaterialTheme.colorScheme.primary
                                bike.batteryLevel >= 40 -> MaterialTheme.colorScheme.secondary
                                else -> MaterialTheme.colorScheme.error
                            }
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(R.string.bike_distance_format, Random.nextDouble(0.1, 0.3)),
                            style = MaterialTheme.typography.labelSmall
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { onViewDetails(bike.uuid) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.view_details))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun getBatteryLabelRes(level: Int): Int {
    return when {
        level >= 75 -> R.string.battery_high
        level >= 40 -> R.string.battery_medium
        else -> R.string.battery_low
    }
}
