package cat.deim.asm34.patinfly.domain.models

data class User(
    val uuid: String,
    val name: String,
    val email: String,
    val hashedPassword: String,
    val creationDate: String,
    val lastConnection: String,
    val deviceId: String
)