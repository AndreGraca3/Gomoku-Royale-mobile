package pt.isel.gomoku

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pt.isel.gomoku.http.service.interfaces.LeaderBoardService
import pt.isel.gomoku.http.service.interfaces.MatchService
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.repository.interfaces.TokenRepository

interface DependenciesContainer {
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

    val userService: UserService

    val matchService: MatchService

    val tokenRepository: TokenRepository
}