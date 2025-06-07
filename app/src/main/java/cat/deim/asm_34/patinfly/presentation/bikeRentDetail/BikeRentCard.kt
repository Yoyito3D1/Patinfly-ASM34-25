package cat.deim.asm_34.patinfly.presentation.bikeRentDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.deim.asm_34.patinfly.R
import cat.deim.asm_34.patinfly.domain.models.Bike
import coil.compose.AsyncImage
import java.text.DateFormat

@Composable
fun BikeRentCard(
    bike: Bike?,
    loading: Boolean,
    onToggleRent: () -> Unit
) {
    if (loading) { CircularProgressIndicator(); return }
    bike ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        /* --- Cabecera básica reutilizada --- */
        Text(bike.bikeType.name, style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(24.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.bike_sample),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (bike.isRented) "Rented" else "Available",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(bike.name, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Divider(Modifier.padding(vertical = 16.dp))

                DetailRow(Icons.Default.BatteryFull, "${bike.batteryLevel}%")
                DetailRow(Icons.Default.Place, "${bike.meters} meters")
                DetailRow(
                    Icons.Default.CalendarMonth,
                    DateFormat.getDateInstance().format(bike.creationDate)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        val (label, color) = if (bike.isRented)
            "Stop rent" to 0xFFFF4D6D      // rojo
        else
            "Rent"      to 0xFFE0F2BE      // verde

        Button(
            onClick  = onToggleRent,
            colors   = ButtonDefaults.buttonColors(containerColor = Color(color)),
            shape    = RoundedCornerShape(22.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) { Text(label, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) }

        Spacer(Modifier.height(16.dp))

        /* --- Mapa estático --- */
        val url = staticMapUrl(bike.latitude, bike.longitude)
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
private fun DetailRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

private fun staticMapUrl(lat: Double, lon: Double): String =
    "https://staticmap.openstreetmap.de/staticmap.php" +
            "?center=$lat,$lon&zoom=15&size=600x300&markers=$lat,$lon,ol-marker-green"
