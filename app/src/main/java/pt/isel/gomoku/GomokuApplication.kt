package pt.isel.gomoku

import android.app.Application
import okhttp3.OkHttpClient
import com.google.gson.Gson
import pt.isel.gomoku.http.service.LeaderBoardService
import pt.isel.gomoku.http.service.gomokuroyale.LeaderBoardServiceImpl
import java.util.concurrent.TimeUnit

interface GomokuDependencyProvider {
    /**
     * The HTTP client used to perform HTTP requests
     */
    val httpClient: OkHttpClient

    /**
     * The JSON serializer/deserializer used to convert JSON into DTOs
     */
    val gson: Gson

    /**
     * The service used to perform leaderboard requests
     */
    val leaderBoardService: LeaderBoardService
}

class GomokuApplication: Application(), GomokuDependencyProvider {
    override val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .build()

    override val gson: Gson = Gson()

    override val leaderBoardService: LeaderBoardService
        get() = LeaderBoardServiceImpl(httpClient, gson)
}