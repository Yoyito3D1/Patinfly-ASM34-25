package cat.deim.asm34.patinfly.domain.repository

import cat.deim.asm34.patinfly.data.datasource.model.BikeModel

interface IBikeDataSource {
    fun getAllBikes(): List<BikeModel>
}
