package pt.isel.gomoku.http.service.interfaces

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.http.model.MatchCreationInputModel
import pt.isel.gomoku.http.model.MatchCreationOutputModel
import pt.isel.gomoku.http.model.PlayOutputModel

interface MatchService {

    val client: OkHttpClient
    val gson: Gson

    suspend fun createMatch(input: MatchCreationInputModel): MatchCreationOutputModel
    suspend fun getMatchById(id: String): Match
    suspend fun play(id: String, move: Dot): PlayOutputModel
    suspend fun deleteSetupMatch()
}