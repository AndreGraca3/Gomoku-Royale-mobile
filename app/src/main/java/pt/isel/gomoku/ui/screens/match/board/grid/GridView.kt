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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.http.model.PlayOutputModel

@Composable
fun GridView(
    board: Board,
    selector: Dot?,
    pendingPlay: IOState<PlayOutputModel>,
    onCellClick: (Dot) -> Unit
) {
    val shape = RoundedCornerShape(10.dp)
    val pendingPlayValue = pendingPlay.getOrNull()

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
                Cell(
                    stone,
                    pendingStone =
                    if (pendingPlayValue != null && pendingPlayValue.stone.dot == dot)
                        pendingPlayValue.stone else null,
                    isSelected = selector == dot,
                    onClick = { onCellClick(dot) })
            }
        }
    )
}