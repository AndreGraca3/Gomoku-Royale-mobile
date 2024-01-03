package pt.isel.gomoku.ui.screens.match.board.grid

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.match.Player
import pt.isel.gomoku.ui.components.common.Cross

@Composable
fun Cell(stone: Stone?, pendingStone: Stone?, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1F)
            .pointerInput(onClick) { detectTapGestures { onClick() } }
    ) {
        Cross(Color(250, 216, 127, 255).copy(alpha = 0.4F))


        if (pendingStone != null) {
            val transition = rememberInfiniteTransition(label = "")
            val scale by transition.animateFloat(
                initialValue = 0.8F,
                targetValue = 1F,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )
            Image(
                painter = painterResource(getPieceResource(pendingStone)),
                contentDescription = null,
                alpha = 0.5F,
                modifier = Modifier.scale(scale)
            )
        }

        if (isSelected) Selector()

        if (stone != null) {
            Image(
                painter = painterResource(getPieceResource(stone)),
                contentDescription = null,
            )
        }
    }
}

private fun getPieceResource(stone: Stone): Int {
    return if (stone.player == Player.BLACK) R.drawable.black_piece else R.drawable.white_piece
}
