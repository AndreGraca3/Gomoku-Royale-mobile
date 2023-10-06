package pt.isel.gomoku.ui.screens.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.model.Dot
import pt.isel.gomoku.model.BOARD_DIM
import pt.isel.gomoku.model.Board
import pt.isel.gomoku.model.User
import pt.isel.gomoku.model.users
import pt.isel.gomoku.ui.components.Cell
import pt.isel.gomoku.ui.components.PlayerCard
import pt.isel.gomoku.ui.theme.GomokuTheme


@Composable
fun GameScreen() {
    val board = remember { mutableStateOf(Board()) }
    Column {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .border(2.dp, Color.Red)
                .fillMaxWidth()
        ) {
            //PlayerCard(users.first())
            //InvertedPlayerCard(users.last(), Arrangement.End)
        }
        BoardScreen(board)
    }
}

@Composable
fun BoardScreen(board: MutableState<Board>) {
    Column {
        repeat(BOARD_DIM) { row ->
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
                repeat(BOARD_DIM) { col ->
                    val move = board.value.moves.find { it.dot == Dot(row, col) }
                    Cell(move)
                }
            }
        }
    }
}

@Composable
fun InvertedPlayerCard(user: User, arrange: Arrangement.Horizontal) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = arrange,
        modifier = Modifier.background(
            color = Color.White,
            shape = RectangleShape
        )
    ) {
        Column {
            Text(
                text = user.name,
                color = Color.Black
            )
            Text(
                text = "${user.rank}",
                color = Color.Black
            )
        }
        Image(
            painter = painterResource(R.drawable.user_icon),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun GameScreenPreview() {
    GomokuTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                GameScreen()
            }
        }
    }
}
