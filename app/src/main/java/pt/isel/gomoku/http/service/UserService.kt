package pt.isel.gomoku.http.service

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pt.isel.gomoku.domain.user.Token
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.http.model.user.UserCreateInput
import pt.isel.gomoku.http.model.user.UserCredentialsInput
import pt.isel.gomoku.http.model.user.UserIdOutput
import pt.isel.gomoku.http.model.user.UserInfo
import pt.isel.gomoku.http.model.user.UserUpdateInput

interface UserService {

    val client: OkHttpClient
    val gson: Gson

    suspend fun createUser(input: UserCreateInput): UserIdOutput

    suspend fun getUser(id: Int): User

    suspend fun updateUser(token: String, userInput: UserUpdateInput): UserInfo

    suspend fun deleteUser(token: String)

    suspend fun createToken(input: UserCredentialsInput): Token

}