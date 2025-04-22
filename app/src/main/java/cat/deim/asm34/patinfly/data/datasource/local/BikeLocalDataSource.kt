package cat.deim.asm34.patinfly.data.datasource.local


import android.content.Context
import cat.deim.asm34.patinfly.R
import cat.deim.asm34.patinfly.data.datasource.model.BikeModel
import cat.deim.asm34.patinfly.data.datasource.model.BikeJsonWrapper


import cat.deim.asm34.patinfly.domain.repository.IBikeDataSource
import cat.deim.asm34.patinfly.utils.JsonUtils
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class BikeLocalDataSource private constructor() : IBikeDataSource {

    private var context: Context? = null
    private val bikes: MutableList<BikeModel> = mutableListOf()

    companion object {
        @Volatile
        private var instance: BikeLocalDataSource? = null

        fun getInstance(context: Context): BikeLocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: BikeLocalDataSource().also { created ->
                    instance = created
                    created.context = context
                    created.loadBikes()
                }
            }
        }
    }

    private fun loadBikes() {
        context?.let { ctx ->
            val json = JsonUtils.getJsonDataFromRaw(ctx, R.raw.bikes)
            try {
                val wrapper = Gson().fromJson(json, BikeJsonWrapper::class.java)
                val list = wrapper.bike
                println("✅ BIKES CARGADAS DESDE JSON: ${list.size}")
                bikes.clear()
                bikes.addAll(list)
            } catch (e: Exception) {
                println("❌ ERROR CARGANDO bikes.json")
                e.printStackTrace()
            }
        }
    }


    override fun getAllBikes(): List<BikeModel> = bikes
}
