package pt.isel.gomoku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.model.BOARD_DIM
import pt.isel.gomoku.model.Move
import pt.isel.gomoku.model.Player

@Composable
fun Cell(move: Move? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(
                Color(238, 124, 13, 255)
            )
            .border(2.dp, Color.Red) /** Don't forget to take this off! **/
    ) {
        Box(
            modifier = Modifier.weight(1F / BOARD_DIM, false)
        ) {
            Cross()
            if (move != null) {
                val piece =
                    if (move.player == Player.BLACK) R.drawable.black_piece else R.drawable.white_piece
                Image(
                    painter = painterResource(piece),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
