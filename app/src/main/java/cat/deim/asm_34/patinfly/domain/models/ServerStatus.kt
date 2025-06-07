package cat.deim.asm_34.patinfly.domain.models


data class StatusInfo(
    val version: String,
    val build: String,
    val update: String,
    val name: String
)

data class ServerStatus(
    val status: StatusInfo
)
