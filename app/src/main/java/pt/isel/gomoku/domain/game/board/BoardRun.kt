package pt.isel.gomoku.domain.game.board

import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Player
import pt.isel.gomoku.domain.game.cell.Stone

class BoardRun(size: Int, stones: List<Stone> = mutableListOf(), turn: Player = Player.BLACK): Board(size, stones, turn) {

    override fun play(dst: Dot, player: Player): Board {
        return BoardRun(size, stones + Stone(player, dst), player.opposite())
    }
}