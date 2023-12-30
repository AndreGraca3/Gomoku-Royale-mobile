package pt.isel.gomoku.domain.game.board

import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.match.Player

class Board(val size: Int, val stones: List<Stone>, val turn: Player) {
    fun getStoneOrNull(dot: Dot, stones: List<Stone> = this.stones): Stone? {
        return stones.find { it.dot == dot }
    }
}

//class BoardWinner(size: Int, stones: List<Stone>, val winner: Player) : Board(size, stones, winner)

//class BoardDraw(size: Int, stones: List<Stone>, turn: Player) : Board(size, stones, turn)