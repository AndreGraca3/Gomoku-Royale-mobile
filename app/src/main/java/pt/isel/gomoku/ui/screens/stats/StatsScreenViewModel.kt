package pt.isel.gomoku.ui.screens.stats

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
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.domain.loaded
import pt.isel.gomoku.domain.loading
import pt.isel.gomoku.http.model.UserStatsOutputModel
import pt.isel.gomoku.http.service.interfaces.StatsService

class StatsScreenViewModel(
    private val statsService: StatsService
) : ViewModel() {

    companion object {
        fun factory(statsService: StatsService) = viewModelFactory {
            initializer { StatsScreenViewModel(statsService) }
        }
    }

    private val userStatsFlow: MutableStateFlow<IOState<UserStatsOutputModel>> =
        MutableStateFlow(idle())

    val userStats: Flow<IOState<UserStatsOutputModel>>
        get() = userStatsFlow.asStateFlow()

    fun fetchUserStats(id: Int) {
        userStatsFlow.value = loading()
        viewModelScope.launch {
            val result = kotlin.runCatching {
                statsService.getUserStats(id)
            }
            if (result.isFailure) {
                Log.v("user stats", "result failed, removing token from local storage")
            }
            userStatsFlow.value = loaded(result)
            Log.v("user stats", "result of fetchUserStats: $result")
        }
    }
}