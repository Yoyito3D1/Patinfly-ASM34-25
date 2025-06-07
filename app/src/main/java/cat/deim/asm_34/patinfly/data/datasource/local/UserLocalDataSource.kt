package cat.deim.asm_34.patinfly.data.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import cat.deim.asm_34.patinfly.data.datasource.IUserDataSource
import cat.deim.asm_34.patinfly.data.datasource.local.model.UserModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.util.*

class UserDataSource private constructor() : IUserDataSource {

    private var context: Context? = null

    private val usersUUIDMap: MutableMap<UUID, UserModel> = mutableMapOf()
    private val usersMailMap: MutableMap<String, UserModel> = mutableMapOf()

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

    private fun loadUserData() {
        context?.let { ctx ->
            val jsonString = AssetsProvider.getJsonDataFromRawAsset(ctx, "user") ?: return

            parseJson(jsonString)?.forEach { user ->
                save(user)
            }
        }
    }

    // Ahora parseJson devuelve una lista de UserModel
    private fun parseJson(json: String): List<UserModel>? {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create()

        return try {
            gson.fromJson(json, Array<UserModel>::class.java).toList()
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    private fun save(user: UserModel) {
        usersUUIDMap[UUID.fromString(user.uuid)] = user
        usersMailMap[user.email] = user
    }

    // Métodos de la interfaz siguen igual
    override fun getUserByUUID(uuid: UUID): UserModel? = usersUUIDMap[uuid]
    override fun getUserByEmail(email: String): UserModel? = usersMailMap[email]

    override fun setUser(user: UserModel): Boolean {
        return if (!usersUUIDMap.containsKey(UUID.fromString(user.uuid))) {
            save(user)
            true
        } else {
            false
        }
    }

    override fun getUser(): UserModel? = usersUUIDMap.values.firstOrNull()

    override fun updateUser(user: UserModel): UserModel? {
        val uuid = UUID.fromString(user.uuid)
        return if (usersUUIDMap.containsKey(uuid)) {
            save(user)
            user
        } else {
            null
        }
    }

    override fun getAll(): Collection<UserModel> = usersUUIDMap.values

    override fun deleteUser(): UserModel? {
        val user = getUser()
        user?.let {
            usersUUIDMap.remove(UUID.fromString(it.uuid))
            usersMailMap.remove(it.email)
        }
        return user
    }
}
