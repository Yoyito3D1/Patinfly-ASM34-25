package cat.deim.asm_34.patinfly.data.datasource.database.dbdatasource


import androidx.room.*
import cat.deim.asm_34.patinfly.data.datasource.database.model.UserDTO


@Dao
interface UserDatasource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: UserDTO)

    @Update
    suspend fun update(user: UserDTO)

    @Delete
    suspend fun delete(user: UserDTO)

    @Query("SELECT * FROM user WHERE uuid = :uuid")
    suspend fun getUserByUUID(uuid: String): UserDTO?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByMail(email: String): UserDTO?

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<UserDTO>
}


