package cat.deim.asm_34.patinfly.data.datasource.local.model

import cat.deim.asm_34.patinfly.domain.models.BikeType
import java.util.UUID

data class BikeTypeModel(
    val uuid: String,
    val name: String,
    val type: String
) {
    fun toDomain(): BikeType {
        return BikeType(
            uuid = uuid,
            name = name,
            type = type
        )
    }

    companion object {
        fun fromDomain(bikeType: BikeType): BikeTypeModel {
            return BikeTypeModel(
                uuid = bikeType.uuid,
                name = bikeType.name,
                type = bikeType.type
            )
        }
    }
}
