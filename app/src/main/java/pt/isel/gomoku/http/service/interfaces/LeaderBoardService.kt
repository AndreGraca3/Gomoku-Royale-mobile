package pt.isel.gomoku.http.service.interfaces

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pt.isel.gomoku.http.model.stats.LeaderBoard

interface LeaderBoardService {

    val client: OkHttpClient
    val gson: Gson

    suspend fun getTopPlayers(limit: Int): LeaderBoard
}