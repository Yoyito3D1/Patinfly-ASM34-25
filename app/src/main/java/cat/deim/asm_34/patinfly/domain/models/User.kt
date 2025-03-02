package cat.deim.asm_34.patinfly.domain.models

import java.time.LocalDateTime
import java.util.UUID

data class User(
    val uuid: UUID = UUID.randomUUID(),
    var name: String,
    var email: String,
    var hashedPassword: String,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    var lastConnection: LocalDateTime? = null,
    var deviceId: String? = null
)
