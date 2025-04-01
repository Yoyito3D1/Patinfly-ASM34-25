package cat.deim.asm.myapplication.repository

import cat.deim.asm.myapplication.datasource.UserDataSource
import cat.deim.asm.myapplication.entity.User
import cat.deim.asm.myapplication.mapper.toDomain
import cat.deim.asm.myapplication.mapper.toModel
import java.util.UUID

class UserRepository(private val dataSource: UserDataSource) : IUserRepository {

    override fun insert(user: User): Boolean {
        return dataSource.insert(user.toModel())
    }

    override fun getAll(): List<User> {
        return dataSource.getAll().map { it.toDomain() }
    }

    override fun getById(uuid: UUID): User? {
        return dataSource.getById(uuid)?.toDomain()
    }

    override fun getByEmail(email: String): User? {
        return dataSource.getByEmail(email)?.toDomain()
    }
}