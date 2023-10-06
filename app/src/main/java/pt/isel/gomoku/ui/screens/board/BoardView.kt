package pt.isel.gomoku.ui.screens.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.board.Board
import pt.isel.gomoku.domain.game.Dot
import pt.isel.gomoku.ui.components.Cell
import pt.isel.gomoku.utils.playSound

@Composable
fun BoardView(internalBoard: Board, onDotClick: (Dot) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(internalBoard.size),
        content = {
            items(internalBoard.size * internalBoard.size) { idx ->
                val dot = Dot(idx / internalBoard.size, idx % internalBoard.size)
                val stone = internalBoard.getStoneOrNull(dot)
                Cell(stone) {
                    onDotClick(dot)
                }
            }
        }
    )
}