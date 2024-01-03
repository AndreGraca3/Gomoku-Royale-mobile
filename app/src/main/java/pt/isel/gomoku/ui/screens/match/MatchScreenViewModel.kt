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
import pt.isel.gomoku.http.service.interfaces.MatchService
import pt.isel.gomoku.http.service.interfaces.StatsService
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.http.service.result.APIException
import pt.isel.gomoku.http.service.result.APIResult
import pt.isel.gomoku.http.service.result.Failure
import pt.isel.gomoku.http.service.result.Success
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

    private val pendingPlayFow: MutableStateFlow<Dot?> =
        MutableStateFlow(null)

    val pendingPlay: Flow<Dot?>
        get() = pendingPlayFow.asStateFlow()

    var screenState by mutableStateOf(MatchScreenState.WAITING_FOR_OPPONENT)
        private set

    private val eventsFow: MutableStateFlow<MatchEvent> =
        MutableStateFlow(MatchEvent.Setup)

    val events: Flow<MatchEvent>
        get() = eventsFow.asStateFlow()

    fun getMatchAndStartPolling(id: String) {
        if (matchFlow.value !is Idle) return

        matchFlow.value = loading()
        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                matchService.getMatchById(id)
            }
            matchFlow.value = loaded(result)
            polling(id)
        }
    }

    private suspend fun polling(id: String) {
        while (true) {
            if (screenState == MatchScreenState.MATCH_ENDED) break
            val matchResult = runCatchingAPIFailure { matchService.getMatchById(id) }
            matchFlow.value = loaded(matchResult)
            if (!matchResult.isSuccess) break
            val match = matchResult.getOrThrow()
            if (screenState == MatchScreenState.WAITING_FOR_OPPONENT) {
                val currUser = fetchCurrentUser()
                localUser = UserPlayer(
                    currUser,
                    match.getPlayerFromUserId(currUser.id)
                )
                val opponentId =
                    match.getIdFromPlayer(localUser!!.player.opposite()) ?: continue
                opponentUser = UserPlayer(
                    fetchOpponentUser(opponentId),
                    localUser!!.player.opposite()
                )
                screenState = if (match.getPlayerFromUserId(currUser.id) == match.board.turn) {
                    MatchScreenState.MY_TURN
                } else {
                    MatchScreenState.OPPONENT_TURN
                }
                eventsFow.value = MatchEvent.OpponentJoined
            }
            if (screenState == MatchScreenState.OPPONENT_TURN && match.board.turn == localUser?.player) {
                eventsFow.value = MatchEvent.Played(opponentUser!!.player)
                screenState = MatchScreenState.MY_TURN
            }
            if (match.state == MatchState.FINISHED) {
                screenState = MatchScreenState.MATCH_ENDED
                eventsFow.value = MatchEvent.MatchEnded(match.board.turn == localUser?.player)
                break
            }
            delay(POLLING_DELAY)
        }
    }

    fun deleteSetupMatch() {
        viewModelScope.launch {
            runCatchingAPIFailure {
                matchService.deleteSetupMatch()
            }
            eventsFow.value = MatchEvent.DeleteSetupMatch
        }
    }

    fun play(id: String, dot: Dot) {
        val currMatch = matchFlow.value.getOrNull() ?: return
        val local = localUser
        pendingPlayFow.value = dot
        viewModelScope.launch {
            try {
                requireNotNull(local)
                val newMatch = currMatch.play(dot, local.player) // play locally

                val result = runCatchingAPIFailure {
                    matchService.play(id, dot)
                }

                when (result) {
                    is Success -> {
                        matchFlow.value = loaded(APIResult.success(newMatch))
                        if(newMatch.state == MatchState.FINISHED) {
                            screenState = MatchScreenState.MATCH_ENDED
                            eventsFow.value = MatchEvent.MatchEnded(winner = true)
                        } else {
                            eventsFow.value = MatchEvent.Played(local.player)
                            screenState = MatchScreenState.OPPONENT_TURN
                        }
                    }

                    is Failure -> {
                        throw result.exception
                    }
                }
            } catch (e: Throwable) {
                when (e) {
                    is IllegalStateException -> {
                        eventsFow.value = MatchEvent.Error(e.message)
                    }

                    is APIException -> {
                        eventsFow.value = MatchEvent.Error(e.problem.detail)
                    }
                }
            } finally {
                pendingPlayFow.value = null
            }
        }
    }

    fun forfeit(id: String) {
        val currMatch = matchFlow.value.getOrNull() ?: return
        if (screenState == MatchScreenState.FORFEITING) return
        if (screenState == MatchScreenState.MY_TURN || screenState == MatchScreenState.OPPONENT_TURN) {
            screenState = MatchScreenState.FORFEITING
            viewModelScope.launch {
                val result = runCatchingAPIFailure {
                    matchService.forfeit(id)
                }
                when (result) {
                    is Success -> {
                        matchFlow.value = loaded(APIResult.success(currMatch.forfeit(localUser!!.player)))
                        screenState = MatchScreenState.MATCH_ENDED
                        eventsFow.value = MatchEvent.MatchEnded(false)
                    }

                    is Failure -> {
                        eventsFow.value = MatchEvent.Error(result.exception.problem.detail)
                    }
                }
            }
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

enum class MatchScreenState {
    WAITING_FOR_OPPONENT,
    MY_TURN,
    OPPONENT_TURN,
    FORFEITING,
    MATCH_ENDED
}

sealed class MatchEvent {
    data object Setup : MatchEvent()
    class Played(val player: Player) : MatchEvent()
    data object OpponentJoined : MatchEvent()
    class MatchEnded(val winner: Boolean) : MatchEvent()
    data object DeleteSetupMatch : MatchEvent()
    class Error(val errorMessage: String?) : MatchEvent()
}

class UserPlayer(
    val user: User,
    val player: Player
)