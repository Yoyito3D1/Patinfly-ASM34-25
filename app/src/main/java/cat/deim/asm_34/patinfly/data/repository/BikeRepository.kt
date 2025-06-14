package cat.deim.asm_34.patinfly.data.repository

import android.util.Log
import cat.deim.asm_34.patinfly.data.datasource.database.model.BikeDTO
import cat.deim.asm_34.patinfly.data.datasource.database.model.toDomain
import cat.deim.asm_34.patinfly.data.datasource.database.dbdatasource.BikeDatasource
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.BikeAPIDataSource
import cat.deim.asm_34.patinfly.domain.models.Bike
import cat.deim.asm_34.patinfly.domain.models.ServerStatus
import cat.deim.asm_34.patinfly.domain.repository.IBikeRepository
import java.util.UUID

class BikeRepository(
    private val bikeAPIDataSource: BikeAPIDataSource,
    private val bikeDao: BikeDatasource
) : IBikeRepository {

    override suspend fun getAll(token: String): Collection<Bike> {
        Log.d("BikeRepository", "→ getAll()")

        val localBikes = bikeDao.getAll()
        if (localBikes.isNotEmpty()) {
            Log.d("BikeRepository", "   ⚡ BBDD hit: ${localBikes.size} bicis")
            return localBikes.map { it.toDomain() }
        }

        val remoteBikes = bikeAPIDataSource.getAll(token)
        if (remoteBikes.isNotEmpty()) {
            Log.d("BikeRepository", "   🌐 API hit: ${remoteBikes.size} bicis")
            val domainBikes = remoteBikes.map { it.toDomain() }
            domainBikes.map { BikeDTO.fromDomain(it) }.forEach { bikeDao.save(it) }
            return domainBikes
        }

        Log.d("BikeRepository", "   ❌ No s'han trobat bicis")
        return emptyList()
    }

    override suspend fun getById(uuid: String, token: String): Bike? {
        bikeDao.getByUUID(UUID.fromString(uuid))?.toDomain()?.let {
            return it
        }

        val remote = bikeAPIDataSource.getById(uuid, token)
        remote?.let {
            val domain = it.toDomain()
            bikeDao.save(BikeDTO.fromDomain(domain))
            return domain
        }

        return null
    }

    override suspend fun insert(bike: Bike): Boolean {
        bikeDao.save(BikeDTO.fromDomain(bike))
        return true
    }

    override suspend fun update(bike: Bike): Boolean {
        bikeDao.getByUUID(UUID.fromString(bike.uuid))?.let {
            bikeDao.update(BikeDTO.fromDomain(bike))
            return true
        }
        return false
    }

    override suspend fun delete(uuid: String): Boolean {
        bikeDao.getByUUID(UUID.fromString(uuid))?.let {
            bikeDao.delete(it)
            return true
        }
        return false
    }

    override suspend fun status(): ServerStatus {
        val status = bikeAPIDataSource.getServerStatus().toStatusDomain()
        return status
    }

    override suspend fun reserve(uuid: String, token: String): Bike {
        Log.d("BikeRepository", "reserve($uuid)")
        val apiBike = bikeAPIDataSource.reserve(uuid, token)
        val domain  = apiBike.toDomain()
        bikeDao.save(BikeDTO.fromDomain(domain))
        Log.d(
            "BikeRepository",
            "Saved to Room → isReserved=${domain.isReserved}, isRented=${domain.isRented}"
        )
        return domain
    }

    override suspend fun release(uuid: String, token: String): Bike {
        Log.d("BikeRepository", "release($uuid)")
        val apiBike = bikeAPIDataSource.release(uuid, token)
        val domain  = apiBike.toDomain()
        bikeDao.save(BikeDTO.fromDomain(domain))
        Log.d(
            "BikeRepository",
            "Saved to Room → isReserved=${domain.isReserved}, isRented=${domain.isRented}"
        )
        return domain
    }



    /* BikeRepository.kt */

    override suspend fun startRent(uuid: String, token: String): Bike {
        Log.d("BikeRepository", "startRent($uuid)")
        val apiBike = bikeAPIDataSource.startRent(uuid, token)   // BikeApiModel
        val domain  = apiBike.toDomain()
        bikeDao.save(BikeDTO.fromDomain(domain))
        Log.d(
            "BikeRepository",
            "Saved after startRent → isRented=${domain.isRented}, isReserved=${domain.isReserved}"
        )
        return domain
    }

    override suspend fun stopRent(uuid: String, token: String): Bike {
        Log.d("BikeRepository", "stopRent($uuid)")
        val apiBike = bikeAPIDataSource.stopRent(uuid, token)    // BikeApiModel
        val domain  = apiBike.toDomain()
        bikeDao.save(BikeDTO.fromDomain(domain))
        Log.d(
            "BikeRepository",
            "Saved after stopRent → isRented=${domain.isRented}, isReserved=${domain.isReserved}"
        )
        return domain
    }

}
