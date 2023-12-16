package pt.isel.gomoku.http.service.interfaces

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pt.isel.gomoku.domain.game.match.Match

interface MatchService {

    val client: OkHttpClient
    val gson: Gson

    suspend fun getMatchById(id: String): Match
}