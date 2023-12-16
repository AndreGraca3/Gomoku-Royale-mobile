package pt.isel.gomoku.http.service.interfaces

import com.google.gson.Gson
import okhttp3.OkHttpClient
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

    suspend fun getAuthenticatedUser(): UserInfo

    suspend fun getUser(id: Int): User

    suspend fun updateUser(userInput: UserUpdateInput): UserInfo

    suspend fun deleteUser()

    suspend fun createToken(input: UserCredentialsInput)
}