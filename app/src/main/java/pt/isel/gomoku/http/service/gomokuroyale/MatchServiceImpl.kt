package pt.isel.gomoku.http.service.gomokuroyale

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.http.model.MatchCreationInputModel
import pt.isel.gomoku.http.model.MatchCreationOutputModel
import pt.isel.gomoku.http.model.PlayOutputModel
import pt.isel.gomoku.http.service.GomokuService
import pt.isel.gomoku.http.service.interfaces.MatchService
import java.net.URL

private fun GOMOKU_MATCH_BY_ID_URL(id: String) = "${GomokuService.GOMOKU_API_URL}/matches/$id"
private const val GOMOKU_CREATE_MATCH_URL = "${GomokuService.GOMOKU_API_URL}/matches"

class MatchServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val getMatchRequestUrl: (String) -> String = ::GOMOKU_MATCH_BY_ID_URL,
    private val createMatchRequestUrl: URL = URL(GOMOKU_CREATE_MATCH_URL)
) : MatchService, GomokuService() {

    override suspend fun createMatch(input: MatchCreationInputModel) =
        requestHandler<MatchCreationOutputModel>(
            Request.Builder().buildRequest(
                url = createMatchRequestUrl,
                method = HttpMethod.POST,
                input = input
            )
        )

    override suspend fun getMatchById(id: String) =
        requestHandler<Match>(
            Request.Builder().buildRequest(
                url = URL(getMatchRequestUrl(id)),
                method = HttpMethod.GET
            )
        )


    override suspend fun play(id: String, move: Dot) =
        requestHandler<PlayOutputModel>(
            Request.Builder().buildRequest(
                url = URL(getMatchRequestUrl(id)),
                method = HttpMethod.POST,
                input = move
            )
        )

    override suspend fun deleteSetupMatch() =
        requestHandler<Unit>(
            Request.Builder().buildRequest(
                url = createMatchRequestUrl,
                method = HttpMethod.DELETE
            )
        )
}