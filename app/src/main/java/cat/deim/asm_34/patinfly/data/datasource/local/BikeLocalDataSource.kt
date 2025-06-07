package cat.deim.asm_34.patinfly.data.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import cat.deim.asm_34.patinfly.data.datasource.IBikeDataSource
import cat.deim.asm_34.patinfly.data.datasource.local.model.BikeListWrapperModel

import cat.deim.asm_34.patinfly.data.datasource.local.model.BikeModel

import com.google.gson.GsonBuilder

class BikeDataSource private constructor() : IBikeDataSource {

    private var context: Context? = null

    private val bikes: MutableMap<String, BikeModel> = mutableMapOf()

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: BikeDataSource? = null

        fun getInstance(context: Context): BikeDataSource =
            instance ?: synchronized(this) {
                instance ?: BikeDataSource().also {
                    it.context = context
                    it.loadBikeData()
                    instance = it
                }
            }
    }

    private fun loadBikeData() {
        context?.let {
            val json = AssetsProvider.getJsonDataFromRawAsset(it, "bikes")
            json?.let { data ->
                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                val wrapper = gson.fromJson(data, BikeListWrapperModel::class.java)
                wrapper.bike.forEach { bike ->
                    bikes[bike.uuid] = bike
                }
            }
        }
    }

    override fun insert(bike: BikeModel): Boolean {
        if (bikes.containsKey(bike.uuid)) return false
        bikes[bike.uuid] = bike
        return true
    }

    override fun getAll(): Collection<BikeModel> = bikes.values

    override fun getById(uuid: String): BikeModel? = bikes[uuid]

    override fun update(bike: BikeModel): Boolean {
        if (!bikes.containsKey(bike.uuid)) return false
        bikes[bike.uuid] = bike
        return true
    }

    override fun delete(uuid: String): Boolean {
        return bikes.remove(uuid) != null
    }
}
