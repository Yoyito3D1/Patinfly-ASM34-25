package cat.deim.asm_34.patinfly.data.datasource.database.dbdatasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cat.deim.asm_34.patinfly.data.datasource.database.model.SystemPricingPlanDTO


@Dao
interface SystemPricingPlanDatasource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(plan: SystemPricingPlanDTO)
    @Update
    suspend fun update(plan: SystemPricingPlanDTO)
    @Delete
    suspend fun delete(plan: SystemPricingPlanDTO)

    @Query("SELECT * FROM systempricingplan ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getLatest(): SystemPricingPlanDTO?
}