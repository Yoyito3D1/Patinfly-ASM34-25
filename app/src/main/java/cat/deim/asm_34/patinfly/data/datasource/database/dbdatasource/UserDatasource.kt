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


    @Query("SELECT rentalUUID FROM user WHERE uuid = :uuid LIMIT 1")
    suspend fun getRentalUuid(uuid: String): String?

    @Query("SELECT reservedUUID FROM user WHERE uuid = :uuid LIMIT 1")
    suspend fun getReservedUuid(uuid: String): String?

    @Query("UPDATE user SET reservedUUID = :bikeUuid WHERE uuid = :userId")
    suspend fun updateReserved(userId: String, bikeUuid: String?)

    @Query("UPDATE user SET rentalUUID = :bikeUuid WHERE uuid = :userId")
    suspend fun updateRented(userId: String, bikeUuid: String?)
}


