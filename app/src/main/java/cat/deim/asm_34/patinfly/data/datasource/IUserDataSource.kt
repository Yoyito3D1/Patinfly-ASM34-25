package cat.deim.asm_34.patinfly.data.datasource

import cat.deim.asm_34.patinfly.data.datasource.local.model.UserModel
import java.util.UUID

interface IUserDataSource {
    fun setUser(user: UserModel): Boolean
    fun getUser(): UserModel?
    fun updateUser(user: UserModel): UserModel?
    fun deleteUser(): UserModel?
    fun getAll(): Collection<UserModel>
    fun getUserByUUID(uuid: UUID): UserModel?
    fun getUserByEmail(email: String): UserModel?


}