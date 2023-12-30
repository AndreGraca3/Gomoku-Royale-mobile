package pt.isel.gomoku.http.service.gomokuroyale

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import pt.isel.gomoku.http.model.UserCreationInputModel
import pt.isel.gomoku.http.model.UserCredentialsInputModel
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.http.model.UserIdOutputModel
import pt.isel.gomoku.http.model.UserInfo
import pt.isel.gomoku.http.model.UserUpdateInputModel
import pt.isel.gomoku.http.service.GomokuService
import pt.isel.gomoku.http.service.GomokuService.Companion.GOMOKU_API_URL
import pt.isel.gomoku.http.service.interfaces.UserService
import java.net.URL

private const val GOMOKU_USERS_URL = "$GOMOKU_API_URL/users"
private fun GOMOKU_USERS_GET_URL(id: Int) = "$GOMOKU_USERS_URL/$id"
private const val GOMOKU_USERS_TOKEN_URL = "$GOMOKU_USERS_URL/token"
private const val GOMOKU_USERS_AUTH_URL = "$GOMOKU_USERS_URL/me"

class UserServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val getUserRequestUrl: (Int) -> String = ::GOMOKU_USERS_GET_URL,
    private val userTokenRequestUrl: URL = URL(GOMOKU_USERS_TOKEN_URL),
    private val usersRequestUrl: URL = URL(GOMOKU_USERS_URL),
    private val authUserRequestUrl: URL = URL(GOMOKU_USERS_AUTH_URL)
) : UserService, GomokuService() {

    override suspend fun createUser(input: UserCreationInputModel) =
        requestHandler<UserIdOutputModel>(
            request = Request.Builder()
                .buildRequest(
                    url = usersRequestUrl,
                    method = HttpMethod.POST,
                    input = input
                )
        )

    override suspend fun getAuthenticatedUser() =
        requestHandler<UserDetails>(
            request = Request.Builder().buildRequest(
                url = authUserRequestUrl,
                method = HttpMethod.GET
            )
        )

    override suspend fun getUser(id: Int) =
        requestHandler<UserInfo>(
            request = Request.Builder().buildRequest(
                url = URL(getUserRequestUrl(id)),
                method = HttpMethod.GET
            )
        )

    override suspend fun updateUser(userInput: UserUpdateInputModel) =
        requestHandler<UserInfo>(
            request = Request.Builder().buildRequest(
                url = usersRequestUrl,
                input = userInput,
                method = HttpMethod.PATCH
            )
        )

    override suspend fun deleteUser() =
        requestHandler<Unit>(
            request = Request.Builder().buildRequest(
                url = usersRequestUrl,
                method = HttpMethod.DELETE
            )
        )

    override suspend fun createToken(input: UserCredentialsInputModel) =
        requestHandler<Unit>(
            request = Request.Builder().buildRequest(
                url = userTokenRequestUrl,
                input = input,
                method = HttpMethod.PUT
            )
        )
}