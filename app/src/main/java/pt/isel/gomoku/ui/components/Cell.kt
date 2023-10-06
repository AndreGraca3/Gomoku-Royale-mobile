package pt.isel.gomoku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.Stone
import pt.isel.gomoku.utils.noRippleClickable

@Composable
fun Cell(move: Stone? = null, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(Color(153, 75, 14, 255))
            .noRippleClickable { onClick() }
    ) {

        Cross()

        if (move != null) {
            val piece =
                if (move.player == Player.BLACK) R.drawable.black_piece else R.drawable.white_piece
            Image(
                painter = painterResource(piece),
                contentDescription = null,
            )
        }
    }
}
