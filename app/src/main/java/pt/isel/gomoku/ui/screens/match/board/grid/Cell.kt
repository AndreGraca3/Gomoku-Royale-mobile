package pt.isel.gomoku.ui.screens.match.board.grid

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import pt.isel.gomoku.domain.Player
import pt.isel.gomoku.domain.Stone
import pt.isel.gomoku.ui.components.Cross

@Composable
fun Cell(move: Stone?, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1F)
            .clickable { onClick() }
            // .pointerInput(Unit) { detectTapGestures { onClick() } }
        // this causes recomposition problems for some reason
    ) {
        Cross(Color(250, 216, 127, 255).copy(alpha = 0.4F))

        if (isSelected) Selector()

        if (move == null) return

        val piece =
            if (move.player == Player.BLACK) R.drawable.black_piece else R.drawable.white_piece

        Image(
            painter = painterResource(piece),
            contentDescription = null,
        )
    }
}
