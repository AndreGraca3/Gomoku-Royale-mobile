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

private fun buildMatchByIdUrl(id: String) = "${GomokuService.GOMOKU_API_URL}/matches/$id"
private fun buildPlayMatchByIdUrl(id: String) = "${GomokuService.GOMOKU_API_URL}/matches/$id/play"
private fun buildForfeitMatchByIdUrl(id: String) = "${GomokuService.GOMOKU_API_URL}/matches/$id/forfeit"
private const val GOMOKU_CREATE_MATCH_URL = "${GomokuService.GOMOKU_API_URL}/matches"

class MatchServiceImpl(
    override val client: OkHttpClient,
    override val gson: Gson,
    private val getMatchRequestUrl: (String) -> String = ::buildMatchByIdUrl,
    private val getPlayMatchRequestURL: (String) -> String = ::buildPlayMatchByIdUrl,
    private val getForfeitMatchRequestURL: (String) -> String = ::buildForfeitMatchByIdUrl,
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
                url = URL(getPlayMatchRequestURL(id)),
                method = HttpMethod.POST,
                input = move
            )
        )

    override suspend fun forfeit(id: String) =
        requestHandler<Unit>(
            Request.Builder().buildRequest(
                url = URL(getForfeitMatchRequestURL(id)),
                method = HttpMethod.PUT
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