package cat.deim.asm34.patinfly.utils

import android.content.Context

object JsonUtils {

    fun getJsonDataFromRaw(context: Context, rawResId: Int): String? {
        return try {
            context.resources.openRawResource(rawResId).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
