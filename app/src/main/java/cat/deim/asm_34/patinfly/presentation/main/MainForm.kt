package cat.deim.asm_34.patinfly.presentation.main

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cat.deim.asm_34.patinfly.R
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.models.User
import cat.deim.asm_34.patinfly.presentation.bikeDetails.BikeDetailActivity
import cat.deim.asm_34.patinfly.presentation.bikeRentDetail.BikeRentDetailActivity
import cat.deim.asm_34.patinfly.presentation.bikelist.BikeListActivity
import cat.deim.asm_34.patinfly.presentation.profile.ProfileActivity

@Composable
fun MainForm(
    user: User,
    bikes: List<Bike>,
    loadingBikes: Boolean,
    reservedId: String?
) {
    val context = LocalContext.current
    val hasReserved = reservedId != null

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            /* ---------- Header ---------- */
            Text(
                "Patinfly",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            /* ---------- Profile Card ---------- */
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { context.startActivity(Intent(context, ProfileActivity::class.java)) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(MaterialTheme.shapes.extraLarge)
                    )
                    Spacer(Modifier.width(24.dp))
                    Text(
                        "${user.name} account",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            /* ---------- Around you ---------- */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Around you",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    "→",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .clickable {
                            context.startActivity(Intent(context, BikeListActivity::class.java))
                        }
                        .padding(8.dp)
                )
            }

            if (loadingBikes) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    items(bikes) { bike ->
                        BikeAroundItem(bike) {
                            context.startActivity(
                                Intent(context, BikeDetailActivity::class.java)
                                    .putExtra("BIKE_UUID", bike.uuid)
                            )
                        }
                    }
                }
            }

            /* ---------- Categories ---------- */
            CategorySection(
                onUrbanClick = { context.startActivity(Intent(context, BikeListActivity::class.java)) },
                onElectricClick = { context.startActivity(Intent(context, BikeListActivity::class.java)) }
            )
        }

        /* ---------- QR Button ---------- */
        FloatingActionButton(
            onClick = {
                if (hasReserved) {
                    context.startActivity(
                        Intent(context, BikeRentDetailActivity::class.java)
                            .putExtra("BIKE_UUID", reservedId)
                    )
                } else {
                    Toast.makeText(context, "You have no active reservation", Toast.LENGTH_SHORT).show()
                }
            },
            containerColor = if (hasReserved) Color(0xFF00E676) else Color.Gray,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(72.dp)
        ) {
            Text("QR", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

/* ---------- Around-you card ---------- */
@Composable
private fun BikeAroundItem(
    bike: Bike,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Image(
                painter = painterResource(R.drawable.bike_sample),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "${(bike.meters / 1000.0).format(1)} km away",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 12.dp, bottom = 12.dp, top = 4.dp)
            ) {
                Icon(Icons.Default.BatteryFull, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("${bike.batteryLevel}%")
            }
        }
    }
}

/* ---------- Categories block ---------- */
@Composable
private fun CategorySection(
    onUrbanClick: () -> Unit,
    onElectricClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Categories",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryItem(
                iconRes = R.drawable.bike_type_1,
                label = "Urban",
                onClick = onUrbanClick
            )
            CategoryItem(
                iconRes = R.drawable.bike_type_2,
                label = "Electric",
                onClick = onElectricClick
            )
        }
    }
}

@Composable
private fun CategoryItem(iconRes: Int, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(label, style = MaterialTheme.typography.bodySmall)
    }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)
