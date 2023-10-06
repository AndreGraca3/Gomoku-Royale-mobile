package pt.isel.gomoku.domain.board

import pt.isel.gomoku.domain.game.Dot
import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.Stone

class BoardRun(size: Int, stones: List<Stone> = mutableListOf(), turn: Player = Player.BLACK): Board(size, stones, turn) {

    override fun play(dst: Dot, player: Player): Board {
        val newStones = stones.toMutableList()
        newStones.add(Stone(player, dst))
        return BoardRun(size, newStones, player.opposite())
    }
}