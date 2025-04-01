package cat.deim.asm_34.patinfly.domain.usecase

import cat.deim.asm.myapplication.entity.User
import cat.deim.asm.myapplication.presentation.Credentials
import cat.deim.asm.myapplication.repository.IUserRepository

class LoginUsecase(private var userRepository: IUserRepository) {
    fun execute(credentials: Credentials): Boolean{
        //Check if user exists
        val user: User? = userRepository.getUser()


        user?.let {
            if ((user.email == credentials.email) && (user.password == credentials.password)){
                return true


            }else{
                //data can not fetch
                return false
            }
        }
        return false
    }
}