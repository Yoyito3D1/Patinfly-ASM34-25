package cat.deim.asm34.patinfly.data.repository

import cat.deim.asm34.patinfly.domain.models.User
import cat.deim.asm34.patinfly.domain.repository.IUserDataSource
import cat.deim.asm34.patinfly.domain.repository.IUserRepository


class UserRepository(private val dataSource: IUserDataSource) : IUserRepository {

    override fun getUserByEmail(email: String): User? {
        val userModel = dataSource.getUserByEmail(email)

        return userModel?.let {
            User(
                uuid = it.uuid,
                name = it.name,
                email = it.email,
                hashedPassword = it.hashed_password,
                creationDate = it.creation_date,
                lastConnection = it.last_connection,
                deviceId = it.device_id
            )
        }
    }
}
