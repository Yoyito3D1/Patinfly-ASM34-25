package cat.deim.asm.myapplication.repository


import cat.deim.asm.myapplication.entity.User
import java.util.*

interface IUserRepository {
    fun insert(user: User): Boolean
    fun getAll(): List<User>
    fun getById(uuid: UUID): User?
    fun getByEmail(email: String): User?
}
