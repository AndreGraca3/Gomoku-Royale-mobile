package pt.isel.gomoku.domain.game.board

import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.match.Player

data class Board(val size: Int, val stones: List<Stone>, val turn: Player) {

    companion object {
        const val PIECES_TO_WIN = 5
    }

    fun getStoneOrNull(dot: Dot, stones: List<Stone> = this.stones): Stone? {
        return stones.find { it.dot == dot }
    }

    private fun isIdxInBoard(idx: Int): Boolean {
        return idx in 0 until size
    }

    /**
     * Counts the stones of a player in a given direction
     */
    private fun countStones(dot: Dot, player: Player, dx: Int, dy: Int): Int {
        var currentDot = dot
        var count = 0

        while (isIdxInBoard(currentDot.row.index) && isIdxInBoard(currentDot.column.index)) {
            val nextRowIdx = currentDot.row.index + dy
            val nextColumnIdx = currentDot.column.index + dx

            currentDot = Dot(nextRowIdx, nextColumnIdx)
            val stone = getStoneOrNull(currentDot)

            if (stone == null || stone.player != player) break
            count++
        }
        return count
    }

    fun checkWinner(m: Stone): Boolean {
        val dot = m.dot
        val player = m.player

        // Check horizontal
        if (countStones(dot, player, 1, 0) + countStones(dot, player, -1, 0) + 1 >= PIECES_TO_WIN)
            return true

        // Check vertical
        if (countStones(dot, player, 0, 1) + countStones(dot, player, 0, -1) + 1 >= PIECES_TO_WIN)
            return true

        // Check diagonal
        if (countStones(dot, player, 1, -1) + countStones(dot, player, -1, 1) + 1 >= PIECES_TO_WIN)
            return true

        // Check anti-diagonal
        return countStones(dot, player, 1, 1) + countStones(
            dot,
            player,
            -1,
            -1
        ) + 1 >= PIECES_TO_WIN
    }

    /**
     * Plays a stone in the given dot and changes the turn independently of the validity of the play.
     */
    fun play(dst: Dot, player: Player): Board {
        require(turn == player) { "It's not $player's turn." }
        require(isIdxInBoard(dst.row.index) && isIdxInBoard(dst.column.index)) { "$dst is outside of board's limits." }
        require(getStoneOrNull(dst) == null) { "$dst is already occupied." }
        return Board(size, stones + Stone(player, dst), player.opposite())
    }

    fun forfeit(player: Player): Board {
        return Board(size, stones, player.opposite())
    }
}

//class BoardWinner(size: Int, stones: List<Stone>, val winner: Player) : Board(size, stones, winner)

//class BoardDraw(size: Int, stones: List<Stone>, turn: Player) : Board(size, stones, turn)