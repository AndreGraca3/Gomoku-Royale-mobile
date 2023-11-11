package pt.isel.gomoku.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import pt.isel.gomoku.domain.siren.SirenEntity
import pt.isel.gomoku.domain.user.dto.User
import pt.isel.gomoku.domain.user.UserService
import pt.isel.gomoku.domain.user.dto.Token
import pt.isel.gomoku.domain.user.dto.UserCreateInput
import pt.isel.gomoku.domain.user.dto.UserCredentialsInput
import pt.isel.gomoku.domain.user.dto.UserIdOutput
import pt.isel.gomoku.domain.user.dto.UserInfo
import pt.isel.gomoku.domain.user.dto.UserUpdateInput
import java.io.IOException
import java.lang.reflect.Type
import java.net.URL
import kotlin.coroutines.resumeWithException

private const val GOMOKU_API_URL = "http://localhost:8080"
private const val GOMOKU_USERS_URL = "$GOMOKU_API_URL/users"
private const val GOMOKU_USERS_GET_URL = "$GOMOKU_USERS_URL/{id}"
private const val GOMOKU_USERS_TOKEN_URL = "$GOMOKU_USERS_URL/token"

class UserServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val getUserRequestUrl: URL = URL(GOMOKU_USERS_GET_URL),
    private val userTokenRequestUrl: URL = URL(GOMOKU_USERS_TOKEN_URL),
    private val usersRequestUrl: URL = URL(GOMOKU_USERS_URL)
) : UserService {
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
            request = createUserRequest(input),
            block = { body ->
                val type: Type = object : TypeToken<SirenEntity<UserIdOutput>>() {}.type
                return@requestHandler gson.fromJson<SirenEntity<UserIdOutput>>(
                    body.string(),
                    type
                ).properties!!
            }
        )


    override suspend fun getUser(id: Int): User =
        requestHandler(
            request = getUserRequest(id),
            block = { body ->
                val type: Type = object : TypeToken<SirenEntity<User>>() {}.type
                return@requestHandler gson.fromJson<SirenEntity<User>>(
                    body.string(),
                    type
                ).properties!!
            }
        )

    override suspend fun updateUser(token: String, userInput: UserUpdateInput): UserInfo =
        requestHandler(
            request = updateUserRequest(token, userInput),
            block = { body ->
                val type: Type = object : TypeToken<SirenEntity<UserInfo>>() {}.type
                return@requestHandler gson.fromJson<SirenEntity<UserInfo>>(
                    body.string(),
                    type
                ).properties!!
            }
        )

    override suspend fun deleteUser(token: String) =
        requestHandler(
            request = deleteUserRequest(token),
            block = { body ->
                val type: Type = object : TypeToken<SirenEntity<Unit>>() {}.type
                return@requestHandler gson.fromJson<SirenEntity<Unit>>(
                    body.string(),
                    type
                ).properties!!
            }
        )


    override suspend fun createToken(input: UserCredentialsInput): Token =
        requestHandler(
            request = createTokenRequest(input),
            block = { body ->
                val type: Type = object : TypeToken<SirenEntity<Token>>() {}.type
                return@requestHandler gson.fromJson<SirenEntity<Token>>(
                    body.string(),
                    type
                ).properties!!
            }
        )


    private suspend fun <T> requestHandler(request: Request, block: (ResponseBody) -> T): T =
        suspendCancellableCoroutine {
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(UserServiceException("Something went wrong...", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        it.resumeWithException(UserServiceException(response.message))
                    } else {
                        it.resumeWith(Result.success(block(body)))
                    }
                }
            })
            it.invokeOnCancellation { call.cancel() }
        }
}