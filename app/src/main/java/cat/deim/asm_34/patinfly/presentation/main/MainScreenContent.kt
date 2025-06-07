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
import cat.deim.asm_34.patinfly.domain.usecase.GetUserUseCase
import cat.deim.asm_34.patinfly.presentation.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MainScreenContent(
    getUserUseCase: GetUserUseCase,
    getBikesUseCase: GetBikesUseCase
) {
    val context  = LocalContext.current
    val session  = SessionManager(context)
    val token    = session.getToken()

    /* 🔄  Siempre lee la preferencia viva */
    val reservedId: String? = SessionManager(context).getReservedBike()

    var loading by remember { mutableStateOf(true) }
    var user    by remember { mutableStateOf<User?>(null) }
    var bikes   by remember { mutableStateOf<List<Bike>>(emptyList()) }

    LaunchedEffect(token) {
        /* --- Sesión válida --- */
        if (token.isBlank() || session.isTokenExpired()) {
            context.startActivity(Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            return@LaunchedEffect
        }

        /* --- Perfil y bicis --- */
        user = runCatching {
            withContext(Dispatchers.IO) { getUserUseCase.execute(token) }
        }.getOrNull()

        bikes = runCatching {
            withContext(Dispatchers.IO) { getBikesUseCase.execute(token) }
        }.getOrDefault(emptyList())

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
            reservedId   = reservedId        // ← valor actualizado siempre
        )

        else -> context.startActivity(Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }
}

/* Helper local (por si se usa en otras composables de este archivo) */
private fun Double.format(digits: Int) = "%.${digits}f".format(this)
