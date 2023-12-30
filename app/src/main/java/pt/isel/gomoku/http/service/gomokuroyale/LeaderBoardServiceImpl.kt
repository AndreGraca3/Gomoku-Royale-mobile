package pt.isel.gomoku.http.service.gomokuroyale

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import pt.isel.gomoku.http.model.LeaderBoard
import pt.isel.gomoku.http.service.GomokuService
import pt.isel.gomoku.http.service.GomokuService.Companion.GOMOKU_API_URL
import pt.isel.gomoku.http.service.interfaces.LeaderBoardService
import java.net.URL

private fun GOMOKU_LEADERBOARD_URL(limit: Int) = "$GOMOKU_API_URL/stats/users/top?limit=$limit"

class LeaderBoardServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val leaderboardRequestUrlBuilder: (Int) -> String = ::GOMOKU_LEADERBOARD_URL
) : LeaderBoardService, GomokuService() {
    override suspend fun getTopPlayers(limit: Int) =
        requestHandler<LeaderBoard>(
            Request.Builder().buildRequest(
                url = URL(leaderboardRequestUrlBuilder(limit)),
                method = HttpMethod.GET
            )
        )
}