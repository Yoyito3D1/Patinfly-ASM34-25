package cat.deim.asm34.patinfly.domain.repository

import cat.deim.asm34.patinfly.domain.models.User

interface IUserRepository {
    fun getUserByEmail(email: String): User?
}