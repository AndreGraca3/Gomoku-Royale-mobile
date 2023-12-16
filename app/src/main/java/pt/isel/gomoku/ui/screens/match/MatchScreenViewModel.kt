package pt.isel.gomoku.ui.screens.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import pt.isel.gomoku.http.service.interfaces.MatchService
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.ui.screens.login.LoginScreenViewModel

enum class MatchState { IDLE, STARTED, FINISHED }

class MatchScreenViewModel(private val matchService: MatchService) : ViewModel() {
    companion object {
        fun factory(matchService: MatchService) = viewModelFactory {
            initializer { MatchScreenViewModel(matchService) }
        }
    }
}