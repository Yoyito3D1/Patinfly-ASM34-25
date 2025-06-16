package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm_34.patinfly.data.session.SessionManager
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository
import cat.deim.asm_34.patinfly.domain.repository.IUserRepository


class ToggleRentUseCase(
    private val bikeRepo: IBikeRepository,
    private val userRepo: IUserRepository
) {
    suspend fun execute(uuid: String, token: String, isRented: Boolean): Bike {


}
