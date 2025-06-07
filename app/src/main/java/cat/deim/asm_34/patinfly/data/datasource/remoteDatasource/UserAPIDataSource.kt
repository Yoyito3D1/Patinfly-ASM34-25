package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource

import android.util.Log
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.LoginResponseApiModel
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.RentHistoryApiModel
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.UserApiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserAPIDataSource private constructor() {

    companion object {
        private const val BASE_URL = "https://api.patinfly.dev/"
        @Volatile private var instance: UserAPIDataSource? = null

        fun getInstance(): UserAPIDataSource =
            instance ?: synchronized(this) {
                instance ?: UserAPIDataSource().also {
                    instance = it
                    // Creamos Retrofit con Gson
                    retrofitService = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(OkHttpClient.Builder().build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(APIService::class.java)
                }
            }

        private lateinit var retrofitService: APIService
    }

    suspend fun getUser(token: String): UserApiModel = withContext(Dispatchers.IO) {
        val response = retrofitService.getUser(token)
        Log.d("UserAPIDataSource", "GET /api/user → $response")
        response
    }

    suspend fun login(email: String, password: String): LoginResponseApiModel = withContext(Dispatchers.IO) {
        val response = retrofitService.login(email, password)
        Log.d("UserAPIDataSource", "POST /api/login → $response")
        response
    }

    suspend fun getRentHistory(token: String): List<RentHistoryApiModel> =
        withContext(Dispatchers.IO) {
            retrofitService.getUserRentHistory(token).also {
                Log.d("UserAPIDataSource", "GET /api/rent → ${it.size} rentals")
            }
        }
}
