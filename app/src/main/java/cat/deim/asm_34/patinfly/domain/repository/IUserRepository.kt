package cat.deim.asm_34.patinfly.domain.repository

import cat.deim.asm_34.patinfly.domain.models.Rent
import cat.deim.asm_34.patinfly.domain.models.User
import cat.deim.asm_34.patinfly.domain.usecase.Credentials
import java.util.UUID

interface IUserRepository {
    suspend fun setUser(user: User): Boolean
    suspend fun getUser(): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserByUUID(uuid: String): User?
    suspend fun updateUser(user: User): User?
    suspend fun deleteUser(): User?

    suspend fun login(credentials: Credentials): Boolean
    suspend fun getProfile(token: String): User?

    suspend fun getRentHistory(token: String): List<Rent>


}
