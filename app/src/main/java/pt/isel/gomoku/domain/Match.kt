package pt.isel.gomoku.domain

import pt.isel.gomoku.domain.board.Board
import pt.isel.gomoku.domain.board.BoardRun

data class Match(
    val id: String,
    val visibility: String,
    val blackPlayer: User,
    val whitePlayer: User,
    val board: Board = BoardRun(15)
) {
    fun play(dst: Dot, player: Player): Match {
        return copy(board = board.play(dst, player))
    }
}