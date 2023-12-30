package pt.isel.gomoku.http.service.gomokuroyale

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import pt.isel.gomoku.http.model.UserStatsOutputModel
import pt.isel.gomoku.http.service.GomokuService
import pt.isel.gomoku.http.service.GomokuService.Companion.GOMOKU_API_URL
import pt.isel.gomoku.http.service.interfaces.StatsService
import java.net.URL

private fun GOMOKU_USER_STATS_URL(id: Int) = "$GOMOKU_API_URL/stats/users/$id"

class StatsServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val userStatsRequestUrlBuilder: (Int) -> String = ::GOMOKU_USER_STATS_URL
) : StatsService, GomokuService() {
    override suspend fun getUserStats(id: Int) =
        requestHandler<UserStatsOutputModel>(
            Request.Builder().buildRequest(
                url = URL(userStatsRequestUrlBuilder(id)),
                method = HttpMethod.GET
            )
        )
}
