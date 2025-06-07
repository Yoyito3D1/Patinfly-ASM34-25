package cat.deim.asm_34.patinfly.domain.usecase

import android.content.Context
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.BikeAPIDataSource
import cat.deim.asm_34.patinfly.data.repository.BikeRepository
import cat.deim.asm_34.patinfly.domain.models.ServerStatus

class ServerStatusUseCase {
    companion object {
        suspend fun execute(context: Context): ServerStatus {
            val bikeRepository = BikeRepository(
                BikeAPIDataSource.getInstance(context),
                AppDatabase.get(context).bikeDao()
            )
            return bikeRepository.status()
        }
    }
}
