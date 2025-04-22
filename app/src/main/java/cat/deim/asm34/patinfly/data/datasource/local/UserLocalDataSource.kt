package cat.deim.asm34.patinfly.data.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import cat.deim.asm34.patinfly.R
import cat.deim.asm34.patinfly.data.datasource.model.UserModel
import cat.deim.asm34.patinfly.domain.repository.IUserDataSource
import cat.deim.asm34.patinfly.utils.JsonUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException

class UserLocalDataSource private constructor() : IUserDataSource {

    private var context: Context? = null
    private val users: MutableMap<String, UserModel> = mutableMapOf()

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: UserLocalDataSource? = null

        fun getInstance(context: Context): UserLocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: UserLocalDataSource().also { instanceCreated ->
                    instance = instanceCreated
                    instanceCreated.context = context
                    instanceCreated.loadUserData()
                }
            }
        }
    }

    private fun loadUserData() {
        context?.let { ctx ->
            val jsonString = JsonUtils.getJsonDataFromRaw(ctx, R.raw.user)
            jsonString?.let { json ->
                val user = parseJson(json)
                user?.let { userModel ->
                    saveUser(userModel)
                }
            }
        }
    }

    private fun parseJson(json: String): UserModel? {
        val gson: Gson = GsonBuilder().create()
        return try {
            gson.fromJson(json, UserModel::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    private fun saveUser(user: UserModel) {
        users[user.email] = user
    }

    override fun getUserByEmail(email: String): UserModel? {
        return users[email]
    }

    override fun getAllUsers(): List<UserModel> {
        return users.values.toList()
    }
}
