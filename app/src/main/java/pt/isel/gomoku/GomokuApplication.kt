package pt.isel.gomoku

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import pt.isel.gomoku.http.service.gomokuroyale.LeaderBoardServiceImpl
import pt.isel.gomoku.http.service.gomokuroyale.MatchServiceImpl
import pt.isel.gomoku.http.service.gomokuroyale.UserServiceImpl
import pt.isel.gomoku.http.service.interfaces.LeaderBoardService
import pt.isel.gomoku.http.service.interfaces.MatchService
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.repository.infra.TokenRepositoryImpl
import pt.isel.gomoku.repository.interfaces.TokenRepository
import java.util.concurrent.TimeUnit

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

class GomokuApplication : Application(), DependenciesContainer {

    private val cookieJar = MyCookieJar()

    override val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
            .cookieJar(cookieJar)
            .build()

    override val gson: Gson = Gson()

    override val leaderBoardService: LeaderBoardService
        get() = LeaderBoardServiceImpl(httpClient, gson)

    override val userService: UserService
        get() = UserServiceImpl(httpClient, gson)

    override val matchService: MatchService
        get() = MatchServiceImpl(httpClient, gson)

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

    override val tokenRepository: TokenRepository
        get() = TokenRepositoryImpl(dataStore)
}

private class MyCookieJar : CookieJar {
    private val cookiesList = mutableListOf<Cookie>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.find { it.name == "Authorization" }?.let {
            cookiesList.add(it)
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> = cookiesList
}