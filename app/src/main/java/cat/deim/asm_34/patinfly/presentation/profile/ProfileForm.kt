package cat.deim.asm_34.patinfly.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.deim.asm_34.patinfly.R
import cat.deim.asm_34.patinfly.domain.models.*

@Composable
fun ProfileForm(user: User, history: List<Rent>) {

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        /* tarjeta perfil */
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_image),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp).clip(CircleShape)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(user.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Text(user.email, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        /* título */
        item {
            Text(
                "Rental History",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        /* lista */
        if (history.isEmpty()) {
            item { Text("No rentals yet.") }
        } else {
            items(history) { RentCard(it) }
        }
    }
}

@Composable
private fun RentCard(rent: Rent) {

    val date = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
        .withZone(java.time.ZoneId.systemDefault())
        .format(rent.start)

    val duration = rent.end?.let {
        val mins = java.time.Duration.between(rent.start, it).toMinutes()
        "${mins / 60}h ${mins % 60}m"
    } ?: "In progress"

    val costStr = rent.cost?.let { "Cost: %.2f €".format(it) } ?: ""

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("$date – $duration", fontWeight = FontWeight.Medium)
            if (costStr.isNotBlank()) Text(costStr, style = MaterialTheme.typography.bodySmall)
        }
    }
}
