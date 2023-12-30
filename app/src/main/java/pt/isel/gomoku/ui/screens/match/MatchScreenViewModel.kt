package pt.isel.gomoku.ui.screens.match

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.domain.loaded
import pt.isel.gomoku.domain.loading
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.http.model.MatchState
import pt.isel.gomoku.http.service.interfaces.MatchService
import pt.isel.gomoku.http.service.interfaces.StatsService
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.http.service.result.runCatchingAPIFailure

class MatchScreenViewModel(
    private val userService: UserService,
    private val statsService: StatsService,
    private val matchService: MatchService
) : ViewModel() {
    companion object {
        fun factory(
            userService: UserService,
            statsService: StatsService,
            matchService: MatchService
        ) = viewModelFactory {
            initializer { MatchScreenViewModel(userService, statsService, matchService) }
        }
    }

    private val matchFlow: MutableStateFlow<IOState<Match>> = MutableStateFlow(idle())

    val match: Flow<IOState<Match>>
        get() = matchFlow.asStateFlow()

    var currentUser by mutableStateOf<User?>(null)
    var opponentUser by mutableStateOf<User?>(null)

    init {
        viewModelScope.launch {
            fetchCurrentUser()
        }
        matchFlow.value = loading()
    }

    fun getMatch(id: String) {
        viewModelScope.launch {
            val matchResult = runCatchingAPIFailure {
                matchService.getMatchById(id)
            }
            val match = matchResult.getOrNull()
            if (matchResult.isSuccess && match != null) {
                if (match.state != MatchState.SETUP) {
                    Log.v("get match", "found another player...")
                    val opponentId = if (currentUser?.id == match.blackId)
                        match.whiteId else match.blackId
                    fetchOpponentUser(opponentId!!)
                    matchFlow.value = loaded(matchResult)
                }
            }
        }
    }

    fun play(id: String, move: Dot) {
        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                matchService.play(id, move)
            }
            if (result.isFailure) {
                Log.v("play", "result failed, for various reasons...")
            }
        }
    }

    fun deleteSetupMatch() {
        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                matchService.deleteSetupMatch()
            }
            if (result.isFailure) {
                Log.v("deleteMatch", "result failed, for various reasons...")
            }
        }
    }

    private suspend fun fetchCurrentUser() {
        val userResult = runCatchingAPIFailure {
            userService.getAuthenticatedUser()
        }
        val userDetails = userResult.getOrNull()
        if (userResult.isSuccess && userDetails != null) {
            val statsResult = runCatchingAPIFailure {
                statsService.getUserStats(userDetails.id)
            }
            val userStats = statsResult.getOrNull()
            if (statsResult.isSuccess && userStats != null) {
                currentUser = User(
                    id = userDetails.id,
                    name = userDetails.name,
                    avatar = userDetails.avatarUrl,
                    rank = userStats.rank.name
                )
            }
        }
    }

    private suspend fun fetchOpponentUser(id: Int) {
        val userResult = runCatchingAPIFailure {
            userService.getUser(id)
        }
        val userDetails = userResult.getOrNull()
        if (userResult.isSuccess && userDetails != null) {
            val statsResult = runCatchingAPIFailure {
                statsService.getUserStats(id)
            }
            val userStats = statsResult.getOrNull()
            if (statsResult.isSuccess && userStats != null) {
                opponentUser = User(
                    id = userDetails.id,
                    name = userDetails.name,
                    avatar = userDetails.avatarUrl,
                    rank = userStats.rank.name
                )
            }
        }
    }
}