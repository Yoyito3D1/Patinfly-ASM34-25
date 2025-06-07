package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model

data class LoginResponseApiModel(
    val success: Boolean,
    val token: TokenApiModel,
    val version: String
)

data class TokenApiModel(
    val id: Int,
    val email: String,
    val access: String,
    val expires: String,
    val refresh: String,
    val expires_refresh: String
)
