package cat.deim.asm_34.patinfly.presentation.bikelist

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cat.deim.asm_34.patinfly.R
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.presentation.bikeDetails.BikeDetailActivity

@Composable
fun BikeCardItem(
    bike: Bike,
    modifier: Modifier = Modifier
) {
    val context   = LocalContext.current                          // ← contexto Compose
    val cardColor = MaterialTheme.colorScheme.surface
    val labelColor = MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape     = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors    = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Image(
                painter = painterResource(R.drawable.bike_sample),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text  = "${(bike.meters / 1000.0).format(1)} km away",
                    style = MaterialTheme.typography.bodySmall,
                    color = labelColor
                )
                Text(
                    text  = batteryStatus(bike.batteryLevel),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = labelColor
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text  = bike.bikeType.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    Intent(context, BikeDetailActivity::class.java)
                        .putExtra("BIKE_UUID", bike.uuid)
                        .also(context::startActivity)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape  = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2BE))
            ) {
                Text("View Details", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}


private fun batteryStatus(level: Int): String = when {
    level >= 75 -> "High battery"
    level >= 40 -> "Medium battery"
    else        -> "Low battery"
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)
