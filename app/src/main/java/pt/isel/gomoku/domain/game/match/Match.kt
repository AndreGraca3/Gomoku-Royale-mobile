package pt.isel.gomoku.domain.game.match

import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.http.model.MatchState

data class Match(
    val id: String,
    val isPrivate: Boolean,
    val variant: String,
    val blackId: Int,
    val whiteId: Int?,
    val state: MatchState,
    val board: Board,
)