package pt.isel.gomoku.ui.screens.match

import android.util.Log
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
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.http.model.MatchCreationInputModel
import pt.isel.gomoku.http.model.MatchState
import pt.isel.gomoku.http.service.interfaces.MatchService

//enum class MatchState { IDLE, STARTED, FINISHED }

class MatchScreenViewModel(private val matchService: MatchService) : ViewModel() {
    companion object {
        fun factory(matchService: MatchService) = viewModelFactory {
            initializer { MatchScreenViewModel(matchService) }
        }
    }

    private val matchFlow: MutableStateFlow<IOState<Match>> = MutableStateFlow(idle())

    private val currentUserFlow: MutableStateFlow<IOState<User>> = MutableStateFlow(idle())

    private val opponentUserFlow: MutableStateFlow<IOState<User>> = MutableStateFlow(idle())

    val match: Flow<IOState<Match>>
        get() = matchFlow.asStateFlow()

    val currentUser: Flow<IOState<User>>
        get() = currentUserFlow.asStateFlow()

    val opponentUser: Flow<IOState<User>>
        get() = opponentUserFlow.asStateFlow()

    fun createMatch(input: MatchCreationInputModel) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                matchService.createMatch(input)
            }
            if (result.isFailure) {
                // it can result in failure because the user is already in a match.
                Log.v("create match", "result failed, for various reasons...")
            }
            val matchCreationOutput = result.getOrNull()
            if (matchCreationOutput?.state == MatchState.ONGOING) {
                getMatch(matchCreationOutput.id)
            }
            Log.v("create match", "result of createMatch: $result")
        }
    }

    fun getMatch(id: String) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                matchService.getMatchById(id)
            }
            if (result.isFailure) {
                Log.v("get match", "result failed, for various reasons...")
            }
            matchFlow.value = loaded(result)
            Log.v("get match", "result of getMatch: $result")
        }
    }

    fun play(id: String, move: Dot) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                matchService.play(id, move)
            }
            if (result.isFailure) {
                Log.v("play", "result failed, for various reasons...")
            }
            /**I don't like this but works for now!!!**/
            getMatch(id)
        }
    }

    fun deleteSetupMatch(id: String) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                matchService.deleteSetupMatch(id)
            }
            if (result.isFailure) {
                Log.v("play", "result failed, for various reasons...")
            }
        }
    }
}