package pt.isel.gomoku

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import authUser
import com.google.gson.Gson
import dot
import io.mockk.coEvery
import io.mockk.mockk
import leaderBoard
import match
import matchCreationOutput
import okhttp3.OkHttpClient
import playOutput
import pt.isel.gomoku.http.service.interfaces.LeaderBoardService
import pt.isel.gomoku.http.service.interfaces.MatchService
import pt.isel.gomoku.http.service.interfaces.StatsService
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.repository.interfaces.TokenRepository
import user1
import user2
import userIdOutput
import userStats1
import userStats2

/**
 * The service locator to be used in the instrumented tests.
 */
class GomokuTestApplication : DependenciesContainer, Application() {
    override val httpClient: OkHttpClient
        get() = TODO("Not yet implemented")
    override val gson: Gson
        get() = TODO("Not yet implemented")
    override val leaderBoardService: LeaderBoardService
        get() = mockk {
            coEvery { getTopPlayers(any()) } returns leaderBoard
        }
    override var userService: UserService = mockk {
            coEvery { createUser(any()) } returns userIdOutput
            coEvery { getAuthenticatedUser() } returns authUser
            coEvery { getUser(user1.id) } returns user1
            coEvery { getUser(user2.id) } returns user2
            coEvery { updateUser(any()) } returns user1
            coEvery { deleteUser() } returns Unit
            coEvery { createToken(any()) } returns Unit
        }
    override val matchService: MatchService
        get() = mockk {
            coEvery { createMatch(any()) } returns matchCreationOutput
            coEvery { getMatchById(match.id) } returns match
            coEvery { play(match.id, dot) } returns playOutput
            coEvery { deleteSetupMatch() } returns Unit
        }
    override val tokenRepository: TokenRepository
        get() = mockk {
            coEvery { getLocalToken() } returns "token"
        }
    override val statsService: StatsService
        get() = mockk {
            coEvery { getUserStats(1) } returns userStats1
            coEvery { getUserStats(2) } returns userStats2
        }
}

@Suppress("unused")
class GomokuTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, GomokuTestApplication::class.java.name, context)
    }
}