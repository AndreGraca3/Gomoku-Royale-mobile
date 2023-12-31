package pt.isel.gomoku

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import pt.isel.gomoku.http.service.gomokuroyale.LeaderBoardServiceImpl
import pt.isel.gomoku.http.service.gomokuroyale.MatchServiceImpl
import pt.isel.gomoku.http.service.gomokuroyale.StatsServiceImpl
import pt.isel.gomoku.http.service.gomokuroyale.UserServiceImpl
import pt.isel.gomoku.http.service.interfaces.LeaderBoardService
import pt.isel.gomoku.http.service.interfaces.MatchService
import pt.isel.gomoku.http.service.interfaces.StatsService
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.repository.infra.TokenRepositoryDataStore
import pt.isel.gomoku.repository.interfaces.TokenRepository
import java.util.concurrent.TimeUnit

class GomokuApplication : Application(), DependenciesContainer {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

    override val tokenRepository: TokenRepository
        get() = TokenRepositoryDataStore(dataStore)

    override val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .cookieJar(object : CookieJar {
                var authCookie: Cookie? = null

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookies.find { it.name == "Authorization" }?.let {
                        authCookie = it
                        runBlocking {
                            tokenRepository.updateOrRemoveLocalToken(it.value)
                        }
                    }
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    if (authCookie == null) {
                        runBlocking {
                            val token = tokenRepository.getLocalToken()
                            token?.let {
                                authCookie =
                                    Cookie.Builder()
                                        .name("Authorization")
                                        .domain("gomokuroyale.com")
                                        .value(it)
                                        .build()
                            }
                        }
                    }
                    return listOfNotNull(authCookie)
                }
            })
            .build()

    override val gson: Gson = Gson()

    override val leaderBoardService: LeaderBoardService
        get() = LeaderBoardServiceImpl(httpClient, gson)

    override var userService: UserService = UserServiceImpl(httpClient, gson)

    override val statsService: StatsService
        get() = StatsServiceImpl(httpClient, gson)

    override val matchService: MatchService
        get() = MatchServiceImpl(httpClient, gson)
}