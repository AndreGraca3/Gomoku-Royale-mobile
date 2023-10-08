package pt.isel.gomoku.ui.screens.match.board.grid

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import pt.isel.gomoku.R

@Composable
fun Selector() {
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