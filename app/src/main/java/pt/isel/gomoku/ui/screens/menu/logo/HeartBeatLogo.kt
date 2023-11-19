package pt.isel.gomoku.ui.screens.menu.logo

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import pt.isel.gomoku.R

@Composable
fun HeartBeatLogo() {
    val configuration = LocalConfiguration.current
    val label = "HeartBeatLogo"
    val transition = rememberInfiniteTransition(label)
    val scale by transition.animateFloat(
        initialValue = 1F,
        targetValue = 1.02F,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, delayMillis = 2000),
            repeatMode = RepeatMode.Reverse
        ), label
    )

    Box(
        modifier = Modifier.wrapContentSize()
    ) {
        repeat(2) {
            val dir = if (it % 2 == 0) 1 else -1
            BackgroundCloud(
                initialOffsetX = configuration.screenWidthDp.toFloat() + (300 * dir),
                offsetY = configuration.screenHeightDp.toFloat() / (it + 2),
                dir
            )
        }

        Image(
            painter = painterResource(id = R.drawable.royale_games_glowing_logo),
            contentDescription = "Gomoku Logo",
            modifier = Modifier.scale(scale)
        )
    }
}