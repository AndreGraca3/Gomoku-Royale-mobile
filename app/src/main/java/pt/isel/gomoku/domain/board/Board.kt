package pt.isel.gomoku.domain.board

import pt.isel.gomoku.domain.Dot
import pt.isel.gomoku.domain.Player
import pt.isel.gomoku.domain.Stone
import pt.isel.gomoku.domain.toPlayer

abstract class Board(val size: Int, val stones: List<Stone>, val turn: Player) {

    companion object {
        fun deserialize(input: String): Board {
            val lines = input.split("\n")
            val size = lines[0].toInt()
            val kind = lines[1]
            val turn = lines[2][0].toPlayer()
            val stones = lines.drop(3).map { Stone.deserialize(it) }
            return when (kind) {
                BoardDraw::class.simpleName -> BoardDraw(size, stones, turn)
                BoardWinner::class.simpleName -> BoardWinner(size, stones, turn)
                else -> {
                    BoardRun(size, stones, turn)
                }
            }
        }
    }

    fun serialize() =
        "${this::class.simpleName}\n${turn.symbol}\n${stones.joinToString("\n") { it.serialize() }}"

    fun getStoneOrNull(dot: Dot, stones: List<Stone> = this.stones): Stone? {
        return stones.find { it.dot == dot }
    }

    fun isIdxInBoard(idx: Int): Boolean {
        return idx in 0 until size
    }

    /**
     * Counts the stones of a player in a given direction (including current dot)
     */
    fun countStones(dot: Dot, player: Player, dx: Int, dy: Int): Int {
        var currentDot = dot
        var count = 1

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

    abstract fun play(dst: Dot, player: Player): Board
}

class BoardWinner(size: Int, stones: List<Stone>, val winner: Player) : Board(size, stones, winner) {
    override fun play(dst: Dot, player: Player): Board {
        throw IllegalStateException("Player $winner has already won this game.Therefore you can't play in it.")
    }
}

class BoardDraw(size: Int, stones: List<Stone>, turn: Player) : Board(size, stones, turn) {
    override fun play(dst: Dot, player: Player): Board {
        throw IllegalStateException("This game has already finished with a draw.")
    }
}

fun Board.print() {
    val boardGraph = Array(size) { CharArray(size) { '.' } }
    for (stone in stones) {
        val dot = stone.dot
        val playerSymbol = if (stone.player == Player.BLACK) 'B' else 'W'
        boardGraph[size - dot.row.index - 1][dot.column.index] = playerSymbol
    }

    // Print the board
    for (row in boardGraph) {
        println(row.joinToString(" "))
    }
}