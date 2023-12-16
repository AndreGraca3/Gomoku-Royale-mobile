package pt.isel.gomoku.ui.screens.menu.logo

import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R

@Composable
fun HeartBeatLogo(innerPadding: PaddingValues, modifier: Modifier = Modifier) {
    val label = "HeartBeatLogo"
    val transition = rememberInfiniteTransition(label)
    val scale by transition.animateFloat(
        initialValue = 1F,
        targetValue = 1.04F,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, delayMillis = 1500, easing = EaseInBounce),
            repeatMode = RepeatMode.Reverse
        ), label
    )

    Box(
        contentAlignment = androidx.compose.ui.Alignment.Center,
        modifier = modifier.padding(innerPadding)
    ) {
        Image(
            painter = painterResource(id = R.drawable.royale_games_glowing_logo),
            contentDescription = "Gomoku Logo",
            modifier = Modifier.scale(scale)
        )
    }
}

@Preview
@Composable
fun HeartBeatLogoPreview() {
    HeartBeatLogo(PaddingValues(0.dp))
}