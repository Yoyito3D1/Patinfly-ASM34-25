
package cat.deim.asm_34.patinfly.presentation.bikeDetails

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cat.deim.asm_34.patinfly.R
import cat.deim.asm_34.patinfly.domain.models.Bike
import coil.compose.AsyncImage
import java.text.DateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeDetailForm(
    bike: Bike?,
    loading: Boolean,
    onToggleReserve: () -> Unit          // callback ViewModel.toggleReserve()
) {
    val ctx = LocalContext.current

    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    bike ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No bike details available")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        SmallTopAppBar(
            title = { Text("Patinfly") },
            navigationIcon = {
                IconButton(onClick = { (ctx as ComponentActivity).finish() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(4.dp)
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
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = when {
                                bike.isDisabled -> "Disabled"
                                bike.isReserved -> "Reserved"
                                bike.isRented   -> "Rented"
                                else            -> "Available"
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(bike.name, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Divider(Modifier.padding(vertical = 16.dp))

                DetailRow(Icons.Default.BatteryFull, batteryStatus(bike.batteryLevel))
                DetailRow(Icons.Default.Place, "${bike.meters} meters")
                DetailRow(
                    Icons.Default.CalendarMonth,
                    DateFormat.getDateInstance().format(bike.creationDate)
                )
            }
        }

        /* ---------- Botón Reserve / Release ---------- */
        val (label, color) = if (bike.isReserved)
            "Release" to 0xFFD6D6D6          // gris
        else
            "Reserve now" to 0xFFE0F2BE      // verde

        Button(
            onClick  = onToggleReserve,
            colors   = ButtonDefaults.buttonColors(containerColor = Color(color)),
            shape    = RoundedCornerShape(22.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) { Text(label, fontWeight = FontWeight.Bold) }

        /* ---------- Mapa estático ---------- */
        val staticUrl = staticMapUrl(bike.latitude, bike.longitude)

        AsyncImage(
            model = staticUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
private fun DetailRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(12.dp))
        Text(text)
    }
}

private fun batteryStatus(level: Int) = when {
    level >= 75 -> "High battery"
    level >= 40 -> "Medium battery"
    else        -> "Low battery"
}

/** StaticMap OSM con marcador verde. */
private fun staticMapUrl(lat: Double, lon: Double): String =
    "https://staticmap.openstreetmap.de/staticmap.php" +
            "?center=$lat,$lon&zoom=15&size=600x300&markers=$lat,$lon,ol-marker-green"
