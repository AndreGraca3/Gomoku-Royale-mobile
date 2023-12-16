package pt.isel.gomoku.http.service.gomokuroyale

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.http.service.GomokuService
import pt.isel.gomoku.http.service.interfaces.MatchService
import java.net.URL

private fun GOMOKU_MATCH_BY_ID_URL(id: String) =
    "${GomokuService.GOMOKU_API_URL}/matches/$id"

class MatchServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val matchRequestUrlBuilder: (String) -> String = ::GOMOKU_MATCH_BY_ID_URL
) : MatchService, GomokuService() {

    private fun getMatchByIdRequest(id: String) =
        Request.Builder().buildRequest(
            url = URL(matchRequestUrlBuilder(id)),
            method = HttpMethod.GET
        )

    override suspend fun getMatchById(id: String): Match {
        return requestHandler<Match>(
            getMatchByIdRequest(id)
        ).properties
    }
}