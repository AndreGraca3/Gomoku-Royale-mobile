package pt.isel.gomoku.ui.screens.leaderboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.LoadState
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.loaded
import pt.isel.gomoku.domain.stats.UserRank
import pt.isel.gomoku.http.service.interfaces.LeaderBoardService

class LeaderBoardScreenViewModel(private val service: LeaderBoardService) : ViewModel() {
    companion object {
        fun factory(service: LeaderBoardService) = viewModelFactory {
            initializer { LeaderBoardScreenViewModel(service) }
        }

        private const val limit = 10
    }

    var topPlayers by mutableStateOf<LoadState<List<UserRank>>>(Idle)
        private set

    fun loadTopPlayers() {
        viewModelScope.launch {
            topPlayers = Loading
            val res = runCatching { service.getTopPlayers(limit) }
            topPlayers = loaded(res)
        }
    }
}