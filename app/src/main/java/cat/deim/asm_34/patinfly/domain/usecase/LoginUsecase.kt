package cat.deim.asm_34.patinfly.domain.usecase

import android.util.Log
import cat.deim.asm_34.patinfly.data.repository.UserRepository
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository
import cat.deim.asm_34.patinfly.domain.models.User

class LoginUsecase(private var userRepository: UserRepository) {
    suspend fun execute(credentials: Credentials): Boolean {
        return try {
            val isValid = userRepository.login(credentials)

            if (!isValid) {
                Log.d("LoginUsecase", "Invalid credentials")
            }

            isValid
        } catch (error: Exception) {
            Log.e("LoginUsecase", "Error in login process: ${error.message}")
            false
        }
    }
}
