package cat.deim.asm34.patinfly.presentation.profile

import androidx.lifecycle.ViewModel
import cat.deim.asm34.patinfly.domain.models.User

class ProfileViewModel(private val currentUser: User) : ViewModel() {
    fun getUser(): User = currentUser
}
