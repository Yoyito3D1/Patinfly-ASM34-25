package cat.deim.asm_34.patinfly.data.session

import android.content.Context
import android.util.Log
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }

    fun getToken(): String = prefs.getString("token", "") ?: ""

    fun saveExpires(expires: String) {
        prefs.edit().putString("expires", expires).apply()
    }

    fun getExpires(): String = prefs.getString("expires", "") ?: ""


    fun isTokenExpired(): Boolean {
        val expiresStr = getExpires()
        return try {
            val expiresAt = if (expiresStr.endsWith("Z") || expiresStr.contains('+')) {
                OffsetDateTime.parse(expiresStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    .toLocalDateTime()
            } else {
                LocalDateTime.parse(expiresStr, DateTimeFormatter.ISO_DATE_TIME)
            }
            LocalDateTime.now().isAfter(expiresAt)
        } catch (e: Exception) {
            Log.e("SessionManager", "Error parseando expires='$expiresStr': ${e.message}")
            true
        }
    }


    fun clear() {
        prefs.edit().clear().apply()
    }
}
