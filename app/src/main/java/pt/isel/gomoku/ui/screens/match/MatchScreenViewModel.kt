package pt.isel.gomoku.ui.screens.match

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.domain.game.match.Player
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.domain.loaded
import pt.isel.gomoku.domain.loading
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.http.model.MatchState
import pt.isel.gomoku.http.model.PlayOutputModel
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

        private const val POLLING_DELAY = 2000L
    }

    private val matchFlow: MutableStateFlow<IOState<Match>> = MutableStateFlow(idle())

    val match: Flow<IOState<Match>>
        get() = matchFlow.asStateFlow()

    var localUser by mutableStateOf<UserPlayer?>(null)
    var opponentUser by mutableStateOf<UserPlayer?>(null)

    private val pendingPlayFow: MutableStateFlow<IOState<PlayOutputModel>> =
        MutableStateFlow(idle())

    val pendingPlay: Flow<IOState<PlayOutputModel>>
        get() = pendingPlayFow.asStateFlow()

    private val eventsFow: MutableStateFlow<MatchEvent> =
        MutableStateFlow(MatchEvent.SETUP)

    val events: Flow<MatchEvent>
        get() = eventsFow.asStateFlow()

    fun getMatch(id: String) {
        if (matchFlow.value !is Idle) return

        matchFlow.value = loading()
        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                matchService.getMatchById(id)
            }
            // matchFlow.value = loaded(result) // TODO: change this, we use loading to be able to cancel match
            polling(id)
        }
    }

    private fun polling(id: String) {
        viewModelScope.launch {
            while (true) {
                delay(POLLING_DELAY)
                val matchResult = runCatchingAPIFailure { matchService.getMatchById(id) }
                val oldMatch = matchFlow.value.getOrNull()
                if (matchResult.isSuccess) {
                    val match = matchResult.getOrThrow()
                    if (oldMatch != null && oldMatch.state == MatchState.SETUP && match.state == MatchState.ONGOING) {
                        val currUser = fetchCurrentUser()
                        localUser = UserPlayer(
                            currUser,
                            if (match.blackId == currUser.id) Player.BLACK else Player.WHITE
                        )
                        opponentUser = UserPlayer(
                            fetchOpponentUser(
                                if (localUser!!.player == Player.BLACK) match.whiteId!! else match.blackId
                            ), localUser!!.player.opposite()
                        )
                        matchFlow.value = loaded(matchResult)
                    }
                    if (oldMatch != null &&
                        oldMatch.board.turn == opponentUser?.player
                        && match.board.turn == localUser?.player
                    ) {
                        eventsFow.value = MatchEvent.OPPONENT_PLAYED
                    }
                    if (match.state == MatchState.FINISHED) {
                        eventsFow.value = MatchEvent.MATCH_ENDED
                        break
                    }
                }
            }
        }
    }

    fun play(id: String, move: Dot) {
        pendingPlayFow.value = loading()
        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                matchService.play(id, move)
            }
            pendingPlayFow.value = loaded(result)
            if (result.isSuccess) {
                eventsFow.value = MatchEvent.PLAYED
            }
        }
    }

    fun deleteSetupMatch() {
        viewModelScope.launch {
            runCatchingAPIFailure {
                matchService.deleteSetupMatch()
            }
            eventsFow.value = MatchEvent.DELETE_SETUP_MATCH
        }
    }

    private suspend fun fetchCurrentUser(): User {
        val userResult = runCatchingAPIFailure {
            userService.getAuthenticatedUser()
        }
        val userDetails = userResult.getOrThrow()
        val statsResult = runCatchingAPIFailure {
            statsService.getUserStats(userDetails.id)
        }
        val userStats = statsResult.getOrThrow()
        return User(
            id = userDetails.id,
            name = userDetails.name,
            avatar = userDetails.avatarUrl,
            rank = userStats.rank.name
        )
    }

    private suspend fun fetchOpponentUser(id: Int): User {
        val userResult = runCatchingAPIFailure {
            userService.getUser(id)
        }
        val userInfo = userResult.getOrThrow()
        val statsResult = runCatchingAPIFailure {
            statsService.getUserStats(id)
        }
        val userStats = statsResult.getOrThrow()
        return User(
            id = userInfo.id,
            name = userInfo.name,
            avatar = userInfo.avatarUrl,
            rank = userStats.rank.name
        )
    }
}

enum class MatchEvent {
    SETUP, PLAYED, OPPONENT_PLAYED, MATCH_ENDED, DELETE_SETUP_MATCH
}

class UserPlayer(
    val user: User,
    val player: Player
)