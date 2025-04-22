package cat.deim.asm34.patinfly.data.datasource.model

data class UserModel(
    val uuid: String,
    val name: String,
    val email: String,
    val hashed_password: String,
    val creation_date: String,
    val last_connection: String,
    val device_id: String
)