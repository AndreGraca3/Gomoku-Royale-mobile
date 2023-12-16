package pt.isel.gomoku.ui.screens.leaderboard

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
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.domain.loaded
import pt.isel.gomoku.domain.loading
import pt.isel.gomoku.http.model.stats.LeaderBoard
import pt.isel.gomoku.http.service.interfaces.LeaderBoardService

class LeaderBoardScreenViewModel(private val service: LeaderBoardService) : ViewModel() {
    companion object {
        fun factory(service: LeaderBoardService) = viewModelFactory {
            initializer { LeaderBoardScreenViewModel(service) }
        }

        private const val limit = 10
    }

    private val topPlayersFlow: MutableStateFlow<IOState<LeaderBoard>> = MutableStateFlow(idle())

    val topPlayers: Flow<IOState<LeaderBoard>>
        get() = topPlayersFlow.asStateFlow()

    fun loadTopPlayers() {
        topPlayersFlow.value =
            loading() // coroutine may not run immediately so we set the state to loading here
        viewModelScope.launch {
            val res = runCatching { service.getTopPlayers(limit) }
            topPlayersFlow.value = loaded(res)
        }
    }
}