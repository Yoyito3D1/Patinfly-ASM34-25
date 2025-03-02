package cat.deim.asm_34.patinfly.domain.models

import java.util.UUID

data class BykeType(
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val type: String
)
