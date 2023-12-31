package pt.isel.gomoku.http.service.interfaces

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pt.isel.gomoku.http.model.UserCreationInputModel
import pt.isel.gomoku.http.model.UserCredentialsInputModel
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.http.model.UserIdOutputModel
import pt.isel.gomoku.http.model.UserInfo
import pt.isel.gomoku.http.model.UserUpdateInputModel

interface UserService {

    val client: OkHttpClient
    val gson: Gson

    suspend fun createUser(input: UserCreationInputModel): UserIdOutputModel

    suspend fun getAuthenticatedUser(): UserDetails

    suspend fun getUser(id: Int): UserInfo

    suspend fun updateUser(userInput: UserUpdateInputModel): UserDetails

    suspend fun deleteUser()

    suspend fun createToken(input: UserCredentialsInputModel)

    suspend fun deleteToken()
}