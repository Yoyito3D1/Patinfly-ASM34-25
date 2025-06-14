package cat.deim.asm_34.patinfly.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date
import cat.deim.asm_34.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm_34.patinfly.domain.models.DataPlan
import cat.deim.asm_34.patinfly.utils.Converters

@Entity(tableName = "systempricingplan")
@TypeConverters(Converters::class)    
data class SystemPricingPlanDTO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lastUpdated: Date,
    val ttl: Int,
    val version: String,
    val dataPlansJson: String
) {
    companion object {
        private val gson = Gson()
        private val listType = object : TypeToken<List<DataPlan>>() {}.type

        fun fromDomain(domain: SystemPricingPlan) = SystemPricingPlanDTO(
            lastUpdated = domain.lastUpdated,
            ttl = domain.ttl,
            version = domain.version,
            dataPlansJson = gson.toJson(domain.dataPlans, listType)
        )
    }

    fun toDomain(): SystemPricingPlan {
        val gson = Gson()
        val listType = object : TypeToken<List<DataPlan>>() {}.type
        return SystemPricingPlan(
            lastUpdated = lastUpdated,
            ttl = ttl,
            version = version,
            dataPlans = gson.fromJson(dataPlansJson, listType)
        )
    }
}