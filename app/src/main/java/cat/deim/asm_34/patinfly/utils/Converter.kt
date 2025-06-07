package cat.deim.asm_34.patinfly.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class Converters {

    /* ---------- UUID ---------- */
    @TypeConverter
    fun fromUUID(value: UUID?): String? = value?.toString()

    @TypeConverter
    fun toUUID(value: String?): UUID? = value?.let { UUID.fromString(it) }

    /* ---------- Date (ISO-8601) ---------- */
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT)

    @TypeConverter
    fun fromDate(date: Date?): String? = date?.let { formatter.format(it) }

    @TypeConverter
    fun toDate(value: String?): Date? = value?.let { formatter.parse(it) }
}