package cat.deim.asm.myapplication.datasource

import cat.deim.asm.myapplication.model.UserModel
import java.util.UUID


interface IUserDataSource {
    fun insert(user: UserModel): Boolean
    fun getAll(): List<UserModel>
    fun getById(uuid: UUID): UserModel?
    fun getByEmail(email: String): UserModel?
}