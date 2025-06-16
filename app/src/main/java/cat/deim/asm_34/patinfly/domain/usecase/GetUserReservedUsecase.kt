package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm_34.patinfly.data.repository.UserRepository


class GetUserReservedUsecase(
    private val userRepository: UserRepository
) {
    suspend fun execute(userUuid: String): String? =
        userRepository.getReservedUuid(userUuid)
}