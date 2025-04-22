package cat.deim.asm34.patinfly.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.deim.asm34.patinfly.R
import cat.deim.asm34.patinfly.domain.models.User

@Composable
fun ProfileScreen(user: User, onLogoutConfirmed: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.logout_confirm_title)) },
            text = { Text(stringResource(R.string.logout_confirm_text)) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onLogoutConfirmed()
                }) {
                    Text(stringResource(R.string.logout_button))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel_button))
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.profile_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(R.drawable.profile_image),
            contentDescription = stringResource(R.string.profile_picture_desc),
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = stringResource(R.string.profile_name, user.name), fontSize = 18.sp)
        Text(text = stringResource(R.string.profile_email, user.email), fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.profile_creation_date, user.creationDate), fontSize = 14.sp)
        Text(text = stringResource(R.string.profile_last_connection, user.lastConnection), fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { showDialog = true }) {
            Text(text = stringResource(R.string.logout_button))
        }
    }
}

