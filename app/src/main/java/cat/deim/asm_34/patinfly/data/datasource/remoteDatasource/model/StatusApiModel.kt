package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model


import cat.deim.asm_34.patinfly.domain.models.ServerStatus
import cat.deim.asm_34.patinfly.domain.models.StatusInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StatusInfo(
    @SerialName("version")
    val version: String = "0.0",
    @SerialName("build")
    val build: String = "0",
    @SerialName("update")
    val update: String = "2023-05-07T21:50:58.621408Z",
    @SerialName("name")
    val name: String = "testing"
)

@Serializable
data class StatusApiModel(
    @SerialName("status")
    val status: StatusInfo
) {
    fun toStatusDomain(): ServerStatus {
        return ServerStatus(
            status = StatusInfo(
                version = status.version,
                build = status.build,
                update = status.update,
                name = status.name
            )
        )
    }
}