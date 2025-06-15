package cat.deim.asm_34.patinfly.data.repository

import android.util.Log
import cat.deim.asm_34.patinfly.data.datasource.database.model.UserDTO
import cat.deim.asm_34.patinfly.data.datasource.database.model.toDomain
import cat.deim.asm_34.patinfly.data.datasource.database.dbdatasource.UserDatasource
import cat.deim.asm_34.patinfly.data.datasource.local.UserDataSource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.UserAPIDataSource
import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Rent
import cat.deim.asm_34.patinfly.domain.models.User
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository
import cat.deim.asm_34.patinfly.domain.usecase.Credentials

class UserRepository(
    private val localUserDatasource: UserDataSource,
    private val userAPIDataSource: UserAPIDataSource,
    private val userDao: UserDatasource,
    private val sessionManager: SessionManager?
) : IUserRepository {

    override suspend fun getUserByEmail(email: String): User? {
        userDao.getUserByMail(email)?.toDomain()?.let { return it }
        val fromJson = localUserDatasource.getUserByEmail(email)?.toDomain() ?: return null
        userDao.save(UserDTO.fromDomain(fromJson))
        return fromJson
    }

    override suspend fun getUser(): User? {
        userDao.getAll().firstOrNull()?.toDomain()?.let { return it }
        val fromJson = localUserDatasource.getAll().firstOrNull()?.toDomain() ?: return null
        userDao.save(UserDTO.fromDomain(fromJson))
        return fromJson
    }

    override suspend fun getUserByUUID(uuid: String): User? {
        userDao.getUserByUUID(uuid)?.toDomain()?.let { return it }
        val fromJson = localUserDatasource.getAll()
            .firstOrNull { it.uuid == uuid }
            ?.toDomain()
            ?: return null
        userDao.save(UserDTO.fromDomain(fromJson))
        return fromJson
    }

    override suspend fun setUser(user: User): Boolean {
        userDao.save(UserDTO.fromDomain(user))
        return true
    }

    override suspend fun updateUser(user: User): User? {
        userDao.getUserByUUID(user.uuid)?.let {
            userDao.update(UserDTO.fromDomain(user))
            return user
        }
        return null
    }

    override suspend fun deleteUser(): User? {
        val dto = userDao.getAll().firstOrNull() ?: return null
        userDao.delete(dto)
        return dto.toDomain()
    }

    override suspend fun login(credentials: Credentials): Boolean {
        return try {
            Log.d("UserRepository", "login() → iniciando con email=${credentials.email}")

            val response = userAPIDataSource.login(credentials.email, credentials.password)
            Log.d("UserRepository", "login() ← respuesta recibida: success=${response.success}, access=${response.token.access}")

            if (response.success) {
                sessionManager?.saveToken(response.token.access)
                sessionManager?.saveExpires(response.token.expires_refresh)
                Log.d("UserRepository", "login() → token guardado y expiración guardada")
                true
            } else {
                Log.d("UserRepository", "login() → response.success == false")
                false
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "login() → excepción: ${e.message}", e)
            false
        }
    }



    override suspend fun getProfile(token: String): User? {
        Log.d("UserRepository", "getProfile(): token='$token'")
        return try {
            val apiUser = userAPIDataSource.getUser(token)
            Log.d("UserRepository", "getProfile(): apiUser=$apiUser")

            val domainUser = apiUser.toDomain()
            Log.d("UserRepository", "getProfile(): saved to local, returning $domainUser")
            domainUser
        } catch (e: Exception) {
            Log.e("UserRepository", "getProfile(): exception fetching profile", e)
            null
        }
    }

    override suspend fun getRentHistory(token: String): List<Rent> {
        return try {
            userAPIDataSource.getRentHistory(token).map { it.toDomain() }
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 500) {
                Log.w("UserRepository", "Rent history 500 → lista vacía")
                emptyList()
            } else throw e
        }
    }




}
