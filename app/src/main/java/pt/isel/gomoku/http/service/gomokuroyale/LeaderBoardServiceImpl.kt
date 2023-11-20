package pt.isel.gomoku.http.service.gomokuroyale

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import pt.isel.gomoku.domain.stats.UserRank
import pt.isel.gomoku.http.model.PaginationInput
import pt.isel.gomoku.http.service.GomokuService
import pt.isel.gomoku.http.service.GomokuService.Companion.GOMOKU_API_URL
import pt.isel.gomoku.http.service.interfaces.LeaderBoardService
import java.net.URL

private const val GOMOKU_LEADERBOARD_URL = "$GOMOKU_API_URL/stats/top"

class LeaderBoardServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val leaderBoardRequesUrl: URL = URL(GOMOKU_LEADERBOARD_URL),
) : LeaderBoardService, GomokuService() {
    private fun getTopPlayersRequest(input: PaginationInput) =
        Request.Builder()
            .url(leaderBoardRequesUrl)
            .addHeader("accept", "application/json")
            .post(gson.toJson(input).toRequestBody())
            .build()

    override suspend fun getTopPlayers(skip: Int, limit: Int): List<UserRank> {
        return requestHandler(
            getTopPlayersRequest(PaginationInput(skip, limit))
        )
    }
}