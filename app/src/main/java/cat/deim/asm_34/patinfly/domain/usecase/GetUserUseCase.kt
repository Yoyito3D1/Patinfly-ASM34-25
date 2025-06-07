package cat.deim.asm_34.patinfly.domain.usecase

import android.util.Log
import cat.deim.asm_34.patinfly.domain.models.User
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository



class GetUserUseCase(private val userRepository: IUserRepository) {
    companion object {
        private const val TAG = "GetUserUseCase"
    }

    suspend fun execute(token: String): User? {
        Log.d(TAG, "execute(): fetching profile with token=$token")
        val remote = try {
            userRepository.getProfile(token)
        } catch (e: Exception) {
            Log.e(TAG, "execute(): exception in getProfile()", e)
            null
        }

        if (remote != null) {
            Log.d(TAG, "execute(): got remote user: ${remote.email} (uuid=${remote.uuid}) — saving to local")
            userRepository.setUser(remote)
        } else {
            Log.d(TAG, "execute(): no user returned from remote")
        }

        return remote
    }
}
