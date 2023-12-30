package pt.isel.gomoku.ui.screens.match.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Player
import pt.isel.gomoku.ui.screens.match.board.grid.GridView
import pt.isel.gomoku.utils.playSound

@Composable
fun BoardView(board: Board, onCellClick: (Dot) -> Unit) {

    val ctx = LocalContext.current
    var selector by remember { mutableStateOf<Dot?>(null) }

    Box(
        Modifier
            .padding(8.dp)
            .aspectRatio(1F)
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.board_texture),
            contentDescription = "Board borders",
            contentScale = ContentScale.FillBounds,
        )

        GridView(board, selector, onCellClick = { dot ->
            if (board.getStoneOrNull(dot) == null) {
                if (selector == null || dot != selector) {
                    selector = dot
                    ctx.playSound(R.raw.selector)
                } else {
                    onCellClick(dot)
                    selector = null
                }
            }
        })
    }
}

@Preview
@Composable
fun BoardViewPreview() {
    BoardView(
        board = Board(15, emptyList(), Player.BLACK),
        onCellClick = {}
    )
}