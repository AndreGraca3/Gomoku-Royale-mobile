package pt.isel.gomoku.ui.screens.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.board.Board
import pt.isel.gomoku.domain.board.BoardRun
import pt.isel.gomoku.domain.game.Dot
import pt.isel.gomoku.ui.components.Cell

@Composable
fun BoardView(internalBoard: Board, selector: Dot?, onDotClick: (Dot) -> Unit) {
    Box(
        Modifier
            .padding(12.dp)
            .aspectRatio(1F)
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.board_top),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )

        LazyVerticalGrid(columns = GridCells.Fixed(internalBoard.size),
            modifier = Modifier
                .aspectRatio(1F)
                .scale(0.85F)
                .border(1.dp, gridColor, RoundedCornerShape(4.dp))
                .background(Color(12, 78, 1, 255)),
            content = {
                items(internalBoard.size * internalBoard.size) { idx ->
                    val dot = Dot(idx / internalBoard.size, idx % internalBoard.size)
                    val stone = internalBoard.getStoneOrNull(dot)
                    Cell(stone, isSelected = selector == dot) { onDotClick(dot) }
                }
            }
        )
    }
}

@Preview
@Composable
fun BoardViewPreview() {
    BoardView(BoardRun(15), null) {}
}