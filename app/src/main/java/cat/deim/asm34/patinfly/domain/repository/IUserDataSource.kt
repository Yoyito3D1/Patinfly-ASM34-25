package cat.deim.asm34.patinfly.domain.repository

import cat.deim.asm34.patinfly.data.datasource.model.UserModel

interface IUserDataSource {
    fun getUserByEmail(email: String): UserModel?
    fun getAllUsers(): List<UserModel>
}