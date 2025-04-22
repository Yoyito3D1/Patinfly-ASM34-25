package cat.deim.asm34.patinfly.data.datasource.model

import cat.deim.asm34.patinfly.data.datasource.model.BikeTypeModel


data class BikeModel(
    val uuid: String,
    val name: String,
    val bike_type: BikeTypeModel,
    val creation_date: String,
    val last_maintenance_date: String?,
    val in_maintenance: Boolean,
    val is_active: Boolean,
    val is_deleted: Boolean,
    val battery_level: Int,
    val meters: Int
)
