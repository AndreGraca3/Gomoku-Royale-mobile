package pt.isel.gomoku.http.service.gomokuroyale

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import pt.isel.gomoku.domain.stats.UserRank
import pt.isel.gomoku.domain.user.Token
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.http.model.siren.SirenEntity
import pt.isel.gomoku.http.model.user.UserCreateInput
import pt.isel.gomoku.http.model.user.UserCredentialsInput
import pt.isel.gomoku.http.model.user.UserIdOutput
import pt.isel.gomoku.http.model.user.UserInfo
import pt.isel.gomoku.http.model.user.UserUpdateInput
import pt.isel.gomoku.http.service.LeaderBoardService
import pt.isel.gomoku.http.service.UserService
import pt.isel.gomoku.http.service.UserServiceException
import pt.isel.gomoku.http.service.parameterizedURL
import java.io.IOException
import java.lang.reflect.Type
import java.net.URL
import kotlin.coroutines.resumeWithException

private const val GOMOKU_API_URL = "http://localhost:8080"
private const val GOMOKU_LEADERBOARD_URL = "$GOMOKU_API_URL/stats/top"

class LeaderBoardServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val leaderBoardRequesUrl: URL = URL(GOMOKU_LEADERBOARD_URL),
) : LeaderBoardService {
    private fun createUserRequest(input: UserCreateInput) =
        Request.Builder()
            .url(leaderBoardRequesUrl)
            .addHeader("accept", "application/json")
            .post(gson.toJson(input).toRequestBody())
            .build()

    override suspend fun getTopPlayers(skip: Int, limit: Int): List<UserRank> {
        TODO("Not yet implemented")
    }

    private suspend fun <T> requestHandler(request: Request): T =
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
                        val type: Type = object : TypeToken<SirenEntity<T>>() {}.type
                        val res = gson.fromJson<SirenEntity<T>>(
                            body.string(),
                            type
                        ).properties!!
                        it.resumeWith(Result.success(res))
                    }
                }
            })
            it.invokeOnCancellation { call.cancel() }
        }
}