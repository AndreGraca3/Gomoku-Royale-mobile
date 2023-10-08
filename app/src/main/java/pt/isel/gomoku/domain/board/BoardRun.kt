package pt.isel.gomoku.domain.board

import pt.isel.gomoku.domain.Dot
import pt.isel.gomoku.domain.Player
import pt.isel.gomoku.domain.Stone

class BoardRun(size: Int, stones: List<Stone> = mutableListOf(), turn: Player = Player.BLACK): Board(size, stones, turn) {

    override fun play(dst: Dot, player: Player): Board {
        return BoardRun(size, stones + Stone(player, dst), player.opposite())
    }
}