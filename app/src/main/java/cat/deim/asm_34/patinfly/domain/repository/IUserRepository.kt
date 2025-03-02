package cat.deim.asm_34.patinfly.domain.repository

import cat.deim.asm_34.patinfly.domain.models.User

interface IUserRepository {
    fun setUser(user: User): Boolean
    fun getUser(): User?
    fun updateUser(user: User): User?
    fun deleteUser(): User?
}