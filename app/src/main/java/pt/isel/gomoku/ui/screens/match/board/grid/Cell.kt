package pt.isel.gomoku.ui.screens.match.board.grid

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.game.match.Player
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.ui.components.common.Cross

@Composable
fun Cell(stone: Stone?, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1F)
            .pointerInput(stone) { detectTapGestures { onClick() } }
    ) {
        Cross(Color(250, 216, 127, 255).copy(alpha = 0.4F))

        if (isSelected) Selector()

        if (stone == null) return

        val piece =
            if (stone.player == Player.BLACK) R.drawable.black_piece else R.drawable.white_piece

        Image(
            painter = painterResource(piece),
            contentDescription = null,
        )
    }
}
