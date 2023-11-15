package pt.isel.gomoku.ui.screens.menu.logo

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R

@Composable
fun HeartBeatLogo() {
    val label = "HeartBeatLogo"
    val transition = rememberInfiniteTransition(label)
    val scale by transition.animateFloat(
        initialValue = 1F,
        targetValue = 1.03F,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, delayMillis = 2500, easing = EaseIn),
            repeatMode = RepeatMode.Reverse
        ), label
    )

    Image(
        painter = painterResource(id = R.drawable.royale_games_logo),
        contentDescription = "Gomoku Logo",
        modifier = Modifier
            .scale(scale)
            .shadow(60.dp, ambientColor = Color.White, spotColor = Color.White)
            .size(300.dp)
    )
}