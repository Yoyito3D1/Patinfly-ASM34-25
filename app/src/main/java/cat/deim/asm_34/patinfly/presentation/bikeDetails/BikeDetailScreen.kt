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
import androidx.compose.runtime.*
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
import cat.deim.asm_34.patinfly.BuildConfig
import cat.deim.asm_34.patinfly.R
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Bike
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import java.text.DateFormat

enum class ToggleAction { RESERVE, RELEASE, NONE }
private data class Quad<A,B,C,D>(val first: A,val second: B,val third: C,val fourth: D)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeDetailForm(
    bike: Bike?,
    loading: Boolean,
    onToggleAction: (ToggleAction) -> Unit
) {
    val ctx = LocalContext.current
    val userId = remember { SessionManager(ctx).getUserId() }

    if (loading) {
        Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
        return
    }
    bike ?: run {
        Box(Modifier.fillMaxSize(), Alignment.Center) { Text("No bike details available") }
        return
    }

    val (label, color, enabled, action) = when {
        bike.reservedBy == userId -> Quad("Release", 0xFFD6D6D6, true,  ToggleAction.RELEASE)
        bike.reservedBy == null   -> Quad("Reserve now", 0xFFE0F2BE, true,  ToggleAction.RESERVE)
        else                      -> Quad("Reserved", 0xFFD6D6D6, false, ToggleAction.NONE)
    }

    Column(
        Modifier
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
            Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(24.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.bike_sample),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp).clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            when {
                                bike.isDisabled            -> "Disabled"
                                bike.reservedBy == userId   -> "Reserved by you"
                                bike.reservedBy != null     -> "Reserved"
                                bike.isRented              -> "Rented"
                                else                        -> "Available"
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(bike.name, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Divider(Modifier.padding(vertical = 16.dp))

                DetailRow(Icons.Default.BatteryFull, batteryStatus(bike.batteryLevel))
                DetailRow(Icons.Default.Place, "${bike.meters} meters")
                DetailRow(Icons.Default.CalendarMonth,
                    DateFormat.getDateInstance().format(bike.creationDate))
            }
        }

        Button(
            onClick = { if (enabled) onToggleAction(action) },
            enabled = enabled,
            colors  = ButtonDefaults.buttonColors(containerColor = Color(color)),
            shape   = RoundedCornerShape(22.dp),
            modifier = Modifier.fillMaxWidth().height(44.dp)
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) { Text(label, fontWeight = FontWeight.Bold) }

        StaticGeoapifyMap(
            lat = bike.latitude,
            lon = bike.longitude,
            apiKey = BuildConfig.GEOAPIFY_KEY,
            modifier = Modifier.fillMaxWidth().height(260.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun DetailRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, null); Spacer(Modifier.width(12.dp)); Text(text)
    }
}

private fun batteryStatus(level: Int) =
    when { level >= 75 -> "High battery"; level >= 40 -> "Medium battery"; else -> "Low battery" }


private fun geoapifyStaticMapUrl(
    lat: Double,
    lon: Double,
    zoom: Int = 15,
    w:   Int = 600,
    h:   Int = 400,
    apiKey: String
): String = buildString {
    append("https://maps.geoapify.com/v1/staticmap")
    append("?style=osm-bright")
    append("&center=lonlat:$lon,$lat")
    append("&zoom=$zoom")
    append("&marker=lonlat:$lon,$lat;type:material;color:%23ff0000")
    append("&width=$w&height=$h")
    append("&apiKey=$apiKey")
}

@Composable
private fun StaticGeoapifyMap(
    lat: Double,
    lon: Double,
    apiKey: String,
    zoom: Int = 15,
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current
    val url = remember(lat, lon, apiKey, zoom) {
        geoapifyStaticMapUrl(lat, lon, zoom, apiKey = apiKey)
    }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(ctx)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = "Geoapify static map",
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop,
        loading = {
            Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
        },
        error = {
            Image(
                painterResource(R.drawable.map_placeholder),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    )
}
