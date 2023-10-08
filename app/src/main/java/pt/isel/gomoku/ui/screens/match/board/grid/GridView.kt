package pt.isel.gomoku.ui.screens.match.board.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.domain.board.Board
import pt.isel.gomoku.domain.Dot

@Composable
fun GridView(board: Board, selector: Dot?, onDotClick: (Dot) -> Unit) {

    val shape = RoundedCornerShape(10.dp)

    LazyVerticalGrid(columns = GridCells.Fixed(board.size),
        contentPadding = PaddingValues(10.dp),
        userScrollEnabled = false,
        modifier = Modifier
            .aspectRatio(1F)
            .scale(0.85F)
            .clip(shape)
            .background(Color(12, 78, 1, 255))
            .border(1.dp, Color.Black, shape),
        content = {
            items(board.size * board.size) { idx ->
                val dot = Dot(
                    idx / board.size,
                    idx % board.size
                )
                val stone = board.getStoneOrNull(dot)
                Cell(stone, isSelected = selector == dot) { onDotClick(dot) }
            }
        }
    )
}