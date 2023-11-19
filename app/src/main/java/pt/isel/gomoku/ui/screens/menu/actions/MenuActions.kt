package pt.isel.gomoku.ui.screens.menu.actions

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.buttons.AnimatedImageButton

@Composable
fun MenuActions(actions: List<Action>) {
    val transition = rememberInfiniteTransition(label = "bounce")

    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(durationMillis = 500, easing = LinearEasing),
        repeatMode = RepeatMode.Reverse
    )

    val offsetY by transition.animateFloat(
        initialValue = 1F,
        targetValue = 4F,
        animationSpec,
        label = "offsetY"
    )

    val bounceScale by transition.animateFloat(
        initialValue = 0.9F,
        targetValue = 1F,
        animationSpec = animationSpec,
        label = "bounceScale"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        actions.forEach { action ->
            Column(
                modifier = Modifier
                    .offset(y = offsetY.dp)
                    .scale(scaleX = bounceScale, scaleY = 1F)
            ) {
                AnimatedImageButton(id = action.icon, size = 60.dp, onClick = action.onClick)
            }
        }
    }
}