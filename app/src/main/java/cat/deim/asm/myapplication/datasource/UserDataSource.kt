package cat.deim.asm.myapplication.datasource
import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import android.content.Context
import cat.deim.asm.myapplication.model.UserModel
import com.google.gson.JsonSyntaxException
import java.util.UUID



class UserDataSource private constructor() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: UserDataSource? = null

        fun getInstance(context: Context): UserDataSource =
            instance ?: synchronized(this) {
                instance ?: UserDataSource().also {
                    it.context = context
                    it.loadUserData()
                    instance = it
                }
            }
    }

    private var context: Context? = null
    private val usersUUIDMap: MutableMap<UUID, UserModel> = HashMap()
    private val usersMailMap: MutableMap<String, UserModel> = HashMap()

    private fun loadUserData() {
        context?.let { ctx ->
            val json = AssetsProvider.getJsonDataFromRawAsset(ctx, "user")
            if (json != null) {
                val user = parseJson(json)
                if (user != null) {
                    save(user)
                }
            }

        }
    }

    private fun parseJson(json: String): UserModel? {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").create()
        return try {
            gson.fromJson(json, UserModel::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    private fun save(user: UserModel) {
        usersUUIDMap[user.uuid] = user
        usersMailMap[user.email] = user
    }

    fun getById(uuid: UUID): UserModel? = usersUUIDMap[uuid]
    fun getByEmail(email: String): UserModel? = usersMailMap[email]
}
