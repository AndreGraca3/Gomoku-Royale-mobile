package pt.isel.gomoku.domain.user

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pt.isel.gomoku.domain.user.dto.Token
import pt.isel.gomoku.domain.user.dto.User
import pt.isel.gomoku.domain.user.dto.UserCreateInput
import pt.isel.gomoku.domain.user.dto.UserCredentialsInput
import pt.isel.gomoku.domain.user.dto.UserIdOutput
import pt.isel.gomoku.domain.user.dto.UserInfo
import pt.isel.gomoku.domain.user.dto.UserUpdateInput

interface UserService {

    val client: OkHttpClient
    val gson: Gson

    suspend fun createUser(input: UserCreateInput): UserIdOutput

    suspend fun getUser(id: Int): User

    suspend fun updateUser(token: String, userInput: UserUpdateInput): UserInfo

    suspend fun deleteUser(token: String)

    suspend fun createToken(input: UserCredentialsInput): Token

}