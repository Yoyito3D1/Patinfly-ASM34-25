package cat.deim.asm_34.patinfly.domain.usecase

data class Credentials(var email: String = "", var password: String = "") {
    fun isNotEmpty(): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}
