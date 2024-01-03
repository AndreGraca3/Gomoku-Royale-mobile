package pt.isel.gomoku.domain.game.match

import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.http.model.MatchState

data class Match(
    val id: String,
    val isPrivate: Boolean,
    val variant: String,
    val blackId: Int,
    val whiteId: Int?,
    val state: MatchState,
    val board: Board,
) {
    fun getPlayerFromUserId(id: Int?): Player = when (id) {
        blackId -> Player.BLACK
        whiteId -> Player.WHITE
        else -> throw IllegalArgumentException("User with id $id is not a player in this match")
    }

    fun getIdFromPlayer(player: Player): Int? = when (player) {
        Player.BLACK -> blackId
        Player.WHITE -> whiteId
    }

    fun play(dot: Dot, player: Player): Match {
        val newBoard = board.play(dot, player)
        val placedStone = newBoard.stones.last()
        return when {
            board.checkWinner(placedStone) ->
                copy(state = MatchState.FINISHED, board = newBoard.copy(turn = player))

            else -> copy(board = newBoard)
        }
    }

    fun forfeit(player: Player): Match {
        return copy(state = MatchState.FINISHED, board = board.forfeit(player))
    }
}