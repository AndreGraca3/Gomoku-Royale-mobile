package pt.isel.gomoku.ui.screens.menu.logo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pt.isel.gomoku.R

@Composable
fun BackgroundCloud(initialOffsetX: Float, offsetY: Float, direction: Int = 1) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()

    var position by remember { mutableFloatStateOf(initialOffsetX) }

    val horizontalBias by animateFloatAsState(
        targetValue = position,
        animationSpec = tween(60000, easing = LinearEasing),
        label = "",
    )

    LaunchedEffect(Unit) {
        position = initialOffsetX + screenWidth * direction
    }

    Image(
        painter = painterResource(id = R.drawable.cloud_1),
        contentDescription = "Gomoku Logo",
        modifier = Modifier
            .size(50.dp)
            .graphicsLayer {
                translationX = horizontalBias
                translationY = offsetY
            }
    )
}