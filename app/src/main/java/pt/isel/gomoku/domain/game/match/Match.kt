package pt.isel.gomoku.domain.game.match

import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.board.BoardRun
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.user.User

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