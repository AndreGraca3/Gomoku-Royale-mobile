package pt.isel.gomoku.http.service.gomokuroyale

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import pt.isel.gomoku.domain.user.Token
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.http.model.user.UserCreateInput
import pt.isel.gomoku.http.model.user.UserCredentialsInput
import pt.isel.gomoku.http.model.user.UserIdOutput
import pt.isel.gomoku.http.model.user.UserInfo
import pt.isel.gomoku.http.model.user.UserUpdateInput
import pt.isel.gomoku.http.service.GomokuService
import pt.isel.gomoku.http.service.GomokuService.Companion.GOMOKU_API_URL
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.http.service.parameterizedURL
import java.net.URL

private const val GOMOKU_USERS_URL = "$GOMOKU_API_URL/users"
private const val GOMOKU_USERS_GET_URL = "$GOMOKU_USERS_URL/{id}"
private const val GOMOKU_USERS_TOKEN_URL = "$GOMOKU_USERS_URL/token"

class UserServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val getUserRequestUrl: URL = URL(GOMOKU_USERS_GET_URL),
    private val userTokenRequestUrl: URL = URL(GOMOKU_USERS_TOKEN_URL),
    private val usersRequestUrl: URL = URL(GOMOKU_USERS_URL)
) : UserService, GomokuService() {
    private fun createUserRequest(input: UserCreateInput) =
        Request.Builder()
            .url(usersRequestUrl)
            .addHeader("accept", "application/json")
            .post(gson.toJson(input).toRequestBody())
            .build()

    private fun getUserRequest(id: Int) =
        Request.Builder()
            .url(parameterizedURL(getUserRequestUrl, id))
            .addHeader("accept", "application/json")
            .build()

    private fun updateUserRequest(token: String, userInput: UserUpdateInput) =
        Request.Builder()
            .url(usersRequestUrl)
            .addHeader("accept", "application/json")
            .addHeader("authorization", token)
            .put(gson.toJson(userInput).toRequestBody())
            .build()

    private fun deleteUserRequest(token: String) =
        Request.Builder()
            .url(usersRequestUrl)
            .addHeader("accept", "application/json")
            .addHeader("authorization", token)
            .delete()
            .build()

    private fun createTokenRequest(input: UserCredentialsInput) =
        Request.Builder()
            .url(userTokenRequestUrl)
            .addHeader("accept", "application/json")
            .post(gson.toJson(input).toRequestBody())
            .build()

    override suspend fun createUser(input: UserCreateInput): UserIdOutput =
        requestHandler(
            request = createUserRequest(input)
        )


    override suspend fun getUser(id: Int): User =
        requestHandler(
            request = getUserRequest(id)
        )

    override suspend fun updateUser(token: String, userInput: UserUpdateInput): UserInfo =
        requestHandler(
            request = updateUserRequest(token, userInput)
        )

    override suspend fun deleteUser(token: String): Unit =
        requestHandler(
            request = deleteUserRequest(token)
        )


    override suspend fun createToken(input: UserCredentialsInput): Token =
        requestHandler(
            request = createTokenRequest(input)
        )
}