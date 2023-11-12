package pt.isel.gomoku.ui.screens.match

import androidx.lifecycle.ViewModel
import pt.isel.gomoku.domain.game.match.Match

enum class MatchState { IDLE, STARTED, FINISHED }

class MatchScreenViewModel(
    private val match: Match
): ViewModel() {
    // TODO: implement
}