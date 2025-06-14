package cat.deim.asm_34.patinfly.data.datasource.database.dbdatasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cat.deim.asm_34.patinfly.data.datasource.database.model.BikeDTO
import java.util.UUID

@Dao
interface BikeDatasource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(bike: BikeDTO)
    @Update
    suspend fun update(bike: BikeDTO)
    @Delete
    suspend fun delete(bike: BikeDTO)

    @Query("SELECT * FROM bike WHERE uuid = :uuid")
    suspend fun getByUUID(uuid: UUID): BikeDTO?

    @Query("SELECT * FROM bike")
    suspend fun getAll(): List<BikeDTO>
}