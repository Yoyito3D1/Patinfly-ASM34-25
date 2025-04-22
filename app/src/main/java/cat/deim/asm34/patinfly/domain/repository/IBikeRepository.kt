package cat.deim.asm34.patinfly.domain.repository

import cat.deim.asm34.patinfly.domain.models.Bike

interface IBikeRepository {
    fun getAllBikes(): List<Bike>
}
