package pt.isel.gomoku.http.service.interfaces

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pt.isel.gomoku.domain.stats.UserRank

interface LeaderBoardService {

    val client: OkHttpClient
    val gson: Gson

    suspend fun getTopPlayers(skip: Int, limit: Int): List<UserRank>
}