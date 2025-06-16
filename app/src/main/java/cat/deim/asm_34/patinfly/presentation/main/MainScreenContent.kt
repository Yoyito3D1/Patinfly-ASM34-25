package cat.deim.asm_34.patinfly.presentation.main

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.models.User
import cat.deim.asm_34.patinfly.domain.usecase.GetBikesUseCase
import cat.deim.asm_34.patinfly.domain.usecase.GetUserReservedUsecase
import cat.deim.asm_34.patinfly.domain.usecase.GetUserUseCase
import cat.deim.asm_34.patinfly.presentation.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MainScreenContent(
    getUserUseCase:        GetUserUseCase,
    getBikesUseCase:       GetBikesUseCase,
    getUserReservedUseCase:GetUserReservedUsecase,
    refreshTrigger:        Int
) {
    val context = LocalContext.current
    val session = SessionManager(context)
    val token   = session.getToken()

    var loading    by remember { mutableStateOf(true) }
    var user       by remember { mutableStateOf<User?>(null) }
    var bikes      by remember { mutableStateOf<List<Bike>>(emptyList()) }
    var reservedId by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(token, refreshTrigger) {
        if (token.isBlank() || session.isTokenExpired()) {
            context.startActivity(Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            return@LaunchedEffect
        }

        user = withContext(Dispatchers.IO) { getUserUseCase.execute(token) }

        user?.uuid?.let { uuid ->
            reservedId = withContext(Dispatchers.IO) { getUserReservedUseCase.execute(uuid) }
        }

        bikes = withContext(Dispatchers.IO) { getBikesUseCase.execute(token) }
        loading = false
    }

    when {
        loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }
        user != null -> MainForm(
            user         = user!!,
            bikes        = bikes,
            loadingBikes = false,
            reservedId   = reservedId
        )
        else -> context.startActivity(Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }
}
