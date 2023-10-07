package pt.isel.gomoku.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.Stone
import pt.isel.gomoku.ui.screens.board.squareSize

@Composable
fun Cell(move: Stone?, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            //.background(Color(12, 78, 1, 255)) // this causes invisible lines
            .aspectRatio(1F)
            .pointerInput(Unit) { detectTapGestures { onClick() } }
    ) {
        Cross()

        if(isSelected) {
            val transition = rememberInfiniteTransition()
            val scale by transition.animateFloat(
                initialValue = 1F,
                targetValue = 1.5F,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 400),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )

            Image(
                modifier = Modifier.scale(scale),
                painter = painterResource(id = R.drawable.selector),
                contentDescription = null
            )
        }

        if (move == null) return

        val piece =
            if (move.player == Player.BLACK) R.drawable.black_piece else R.drawable.white_piece

        Image(
            painter = painterResource(piece),
            contentDescription = null,
        )
    }
}
