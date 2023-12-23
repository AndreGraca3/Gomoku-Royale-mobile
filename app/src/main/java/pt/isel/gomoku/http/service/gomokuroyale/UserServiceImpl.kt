package pt.isel.gomoku.http.service.gomokuroyale

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.http.model.user.UserCreateInput
import pt.isel.gomoku.http.model.user.UserCredentialsInput
import pt.isel.gomoku.http.model.user.UserDetails
import pt.isel.gomoku.http.model.user.UserIdOutput
import pt.isel.gomoku.http.model.user.UserInfo
import pt.isel.gomoku.http.model.user.UserUpdateInput
import pt.isel.gomoku.http.service.GomokuService
import pt.isel.gomoku.http.service.GomokuService.Companion.GOMOKU_API_URL
import pt.isel.gomoku.http.service.interfaces.UserService
import java.net.URL

private const val GOMOKU_USERS_URL = "$GOMOKU_API_URL/users"
private const val GOMOKU_USERS_GET_URL = "$GOMOKU_USERS_URL/{id}"
private const val GOMOKU_USERS_TOKEN_URL = "$GOMOKU_USERS_URL/token"
private const val GOMOKU_USERS_AUTH_URL = "$GOMOKU_USERS_URL/me"

class UserServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val getUserRequestUrl: URL = URL(GOMOKU_USERS_GET_URL),
    private val userTokenRequestUrl: URL = URL(GOMOKU_USERS_TOKEN_URL),
    private val usersRequestUrl: URL = URL(GOMOKU_USERS_URL),
    private val authUserRequestUrl: URL = URL(GOMOKU_USERS_AUTH_URL)
) : UserService, GomokuService() {

    private fun createUserRequest(input: UserCreateInput) =
        Request.Builder()
            .buildRequest(
                url = usersRequestUrl,
                method = HttpMethod.POST,
                input = input
            )

    private fun getAuthenticatedUserRequest() =
        Request.Builder().buildRequest(
            url = authUserRequestUrl,
            method = HttpMethod.GET
        )

    private fun getUserRequest(id: Int) =
        Request.Builder().buildRequest(
            url = getUserRequestUrl,
            method = HttpMethod.GET,
            input = id
        )

    private fun updateUserRequest(userInput: UserUpdateInput) =
        Request.Builder().buildRequest(
            url = usersRequestUrl,
            input = userInput,
            method = HttpMethod.PUT
        )

    private fun deleteUserRequest() =
        Request.Builder().buildRequest(
            url = usersRequestUrl,
            method = HttpMethod.DELETE
        )

    private fun createTokenRequest(input: UserCredentialsInput) =
        Request.Builder().buildRequest(
            url = userTokenRequestUrl,
            input = input,
            method = HttpMethod.PUT
        )

    override suspend fun createUser(input: UserCreateInput): UserIdOutput =
        requestHandler<UserIdOutput>(
            request = createUserRequest(input)
        ).properties

    override suspend fun getAuthenticatedUser(): UserDetails =
        requestHandler<UserDetails>(
            request = getAuthenticatedUserRequest()
        ).properties

    override suspend fun getUser(id: Int): User =
        requestHandler<User>(
            request = getUserRequest(id)
        ).properties

    override suspend fun updateUser(userInput: UserUpdateInput): UserInfo =
        requestHandler<UserInfo>(
            request = updateUserRequest(userInput)
        ).properties

    override suspend fun deleteUser(): Unit =
        requestHandler<Unit>(
            request = deleteUserRequest()
        ).properties

    override suspend fun createToken(input: UserCredentialsInput) =
        requestHandler<Unit>(
            request = createTokenRequest(input)
        ).properties
}