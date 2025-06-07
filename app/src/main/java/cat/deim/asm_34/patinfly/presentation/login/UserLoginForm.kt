package cat.deim.asm_34.patinfly.presentation.login

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cat.deim.asm_34.patinfly.R
import cat.deim.asm_34.patinfly.domain.usecase.Credentials
import cat.deim.asm_34.patinfly.domain.usecase.LoginUsecase
import cat.deim.asm_34.patinfly.presentation.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UserLoginForm(loginUsecase: LoginUsecase?) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = context as? Activity

    var credentials by remember { mutableStateOf(Credentials()) }
    var loginFailed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.login_image),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Patinfly",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black)
            )
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = credentials.email,
                onValueChange = {
                    credentials = credentials.copy(email = it)
                    loginFailed = false
                },
                label = { Text("Email") },
                singleLine = true,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = credentials.password,
                onValueChange = {
                    credentials = credentials.copy(password = it)
                    loginFailed = false
                },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            if (loginFailed) {
                Text(
                    text = "Credenciales incorrectas",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    val valid = withContext(Dispatchers.IO) {
                        loginUsecase?.execute(credentials) ?: false
                    }
                    if (valid) {
                        val intent = Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        context.startActivity(intent)
                    } else {
                        loginFailed = true
                    }
                }
            },
            containerColor = Color(0xFFEEFFEC),
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Login",
                modifier = Modifier.size(36.dp),
                tint = Color(0xFF00E676)
            )
        }
    }
}
