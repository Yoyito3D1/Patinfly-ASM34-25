package cat.deim.asm_34.patinfly.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cat.deim.asm_34.patinfly.data.datasource.database.model.BikeDTO
import cat.deim.asm_34.patinfly.data.datasource.database.model.SystemPricingPlanDTO
import cat.deim.asm_34.patinfly.data.datasource.database.model.UserDTO
import cat.deim.asm_34.patinfly.data.datasource.dbdatasource.BikeDatasource
import cat.deim.asm_34.patinfly.data.datasource.dbdatasource.SystemPricingPlanDatasource
import cat.deim.asm_34.patinfly.data.datasource.dbdatasource.UserDatasource
import cat.deim.asm_34.patinfly.utils.Converters


@Database(
    entities = [
        UserDTO::class,
        BikeDTO::class,
        SystemPricingPlanDTO::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDatasource
    abstract fun bikeDao(): BikeDatasource
    abstract fun systemPricingPlanDao(): SystemPricingPlanDatasource

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "patinfly_24_database"
                ).build().also { INSTANCE = it }
            }


    }
}
