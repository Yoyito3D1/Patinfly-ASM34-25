package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource

import android.util.Log
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.BikeApiModel
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.StatusApiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BikeAPIDataSource private constructor() {

    companion object {
        private const val BASE_URL = "https://api.patinfly.dev/"
        @Volatile private var instance: BikeAPIDataSource? = null
        private lateinit var retrofitService: APIService

        fun getInstance(): BikeAPIDataSource =
            instance ?: synchronized(this) {
                instance ?: BikeAPIDataSource().also {
                    instance = it
                    retrofitService = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(OkHttpClient.Builder().build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(APIService::class.java)
                }
            }
    }

    suspend fun getServerStatus(): StatusApiModel = withContext(Dispatchers.IO) {
        val response = retrofitService.getServerStatus()
        Log.d("BikeAPIDataSource", "GET /status → $response")
        response
    }

    suspend fun getAll(token: String): List<BikeApiModel> = withContext(Dispatchers.IO) {
        val response = retrofitService.getAllBikes(token)
        Log.d("BikeAPIDataSource", "GET /api/vehicle → ${response.vehicles.size} bicis")
        response.vehicles
    }

    suspend fun getById(uuid: String, token: String): BikeApiModel? = withContext(Dispatchers.IO) {
        val response = retrofitService.getBikeById(token, uuid)
        Log.d("BikeAPIDataSource", "GET /api/vehicle/$uuid → $response")
        response
    }




    suspend fun reserve(uuid: String, token: String): BikeApiModel =
        withContext(Dispatchers.IO) {
            Log.d("BikeAPIDataSource", "→ POST /api/vehicle/reserve/$uuid")
            val resp = retrofitService.reserveBike(token = token, uuid = uuid)
            Log.d(
                "BikeAPIDataSource",
                "← success=${resp.success}, status=${resp.status}, " +
                        "reserved=${resp.vehicle.isReserved}"
            )
            resp.vehicle
        }

    suspend fun release(uuid: String, token: String): BikeApiModel =
        withContext(Dispatchers.IO) {
            Log.d("BikeAPIDataSource", "→ POST /api/vehicle/release/$uuid")
            val resp = retrofitService.releaseBike(token = token, uuid = uuid)
            Log.d(
                "BikeAPIDataSource",
                "← success=${resp.success}, status=${resp.status}, " +
                        "reserved=${resp.vehicle.isReserved}"
            )
            resp.vehicle
        }

    suspend fun startRent(uuid: String, token: String): BikeApiModel =
        withContext(Dispatchers.IO) {
            val resp = retrofitService.startRent(token = token, uuid = uuid)
            Log.d(
                "BikeAPIDataSource",
                "POST /api/rent/start/$uuid → success=${resp.success}, status=${resp.status}"
            )
            resp.vehicle
        }
    
    suspend fun stopRent(uuid: String, token: String): BikeApiModel =
        withContext(Dispatchers.IO) {
            val resp = retrofitService.stopRent(token = token, uuid = uuid)
            Log.d(
                "BikeAPIDataSource",
                "POST /api/rent/stop/$uuid → success=${resp.success}, status=${resp.status}"
            )
            resp.vehicle
        }



}
