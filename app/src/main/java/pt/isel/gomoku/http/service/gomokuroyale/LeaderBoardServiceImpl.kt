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

private fun GOMOKU_LEADERBOARD_URL(limit: Int) =
    "$GOMOKU_API_URL/stats/top?limit=$limit"

class LeaderBoardServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val leaderboardRequestUrlBuilder: (Int) -> String = ::GOMOKU_LEADERBOARD_URL
) : LeaderBoardService, GomokuService() {
    private fun getTopPlayersRequest(input: PaginationInput) =
        Request.Builder()
            .url(leaderboardRequestUrlBuilder(input.limit))
            .addHeader("accept", "application/json")
            .get()
            .build()

    override suspend fun getTopPlayers(limit: Int): List<UserRank> {
        return requestHandler(
            getTopPlayersRequest(PaginationInput(limit))
        )
    }
}