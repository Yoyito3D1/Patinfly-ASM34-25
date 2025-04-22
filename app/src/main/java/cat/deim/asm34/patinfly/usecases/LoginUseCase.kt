package cat.deim.asm34.patinfly.usecases

import cat.deim.asm34.patinfly.domain.models.User
import cat.deim.asm34.patinfly.domain.repository.IUserRepository

class LoginUseCase(private val userRepository: IUserRepository) {


    fun login(email: String, password: String): Boolean {
        val user = userRepository.getUserByEmail(email)
        return user != null && user.hashedPassword == password
    }

    fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }
}
