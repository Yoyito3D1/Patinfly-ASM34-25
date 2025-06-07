package cat.deim.asm_34.patinfly.data.datasource.remoteDatasource


import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.BikeApiModel
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.LoginResponseApiModel
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.RentApiResponse
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.RentHistoryApiModel
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.ReserveReleaseApiResponse
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.StatusApiModel
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.UserApiModel
import cat.deim.asm_34.patinfly.data.datasource.remoteDatasource.model.VehicleListApiResponse
import retrofit2.http.GET
import retrofit2.http.*


interface APIService {

    @GET("status")
    suspend fun getServerStatus(): StatusApiModel

    @GET("api/vehicle")
    suspend fun getAllBikes(
        @Header("Authorization") token: String
    ): VehicleListApiResponse

    @GET("api/vehicle/{uuid}")
    suspend fun getBikeById(
        @Header("Authorization") token: String,
        @Path("uuid") uuid: String
    ): BikeApiModel?

    @POST("api/vehicle/reserve/{uuid}")
    suspend fun reserveBike(
        @Header("Authorization") token: String,
        @Header("Origin") origin: String = "https://api.patinfly.dev",
        @Path("uuid") uuid: String
    ): ReserveReleaseApiResponse          // ← ahora devuelve JSON

    @POST("api/vehicle/release/{uuid}")
    suspend fun releaseBike(
        @Header("Authorization") token: String,
        @Header("Origin") origin: String = "https://api.patinfly.dev",
        @Path("uuid") uuid: String
    ): ReserveReleaseApiResponse


    @POST("api/rent/start/{uuid}")
    suspend fun startRent(
        @Header("Authorization") token: String,
        @Header("Origin") origin: String = "https://api.patinfly.dev",
        @Path("uuid") uuid: String
    ): RentApiResponse

    @POST("api/rent/stop/{uuid}")
    suspend fun stopRent(
        @Header("Authorization") token: String,
        @Header("Origin") origin: String = "https://api.patinfly.dev",
        @Path("uuid") uuid: String
    ): RentApiResponse


    @GET("api/rent")
    suspend fun getUserRentHistory(
        @Header("Authorization") token: String
    ): List<RentHistoryApiModel>

    @GET("api/user")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): UserApiModel

    @POST("api/login")
    suspend fun login(
        @Header("email") email: String,
        @Header("password") password: String,
        @Header("Origin") origin: String = "https://api.patinfly.dev"
    ): LoginResponseApiModel
}
