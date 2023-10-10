package pt.isel.gomoku.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(size = 0.dp),
    borderWidth: Dp = 2.dp,
    borderLeftColor: Color,
    borderRightColor: Color = borderLeftColor,
    backgroundColor: Color,
    animationDuration: Int = 300,
    isLeft: Boolean = true,
    content: @Composable () -> Unit
) {

    val borderColor by animateColorAsState(
        targetValue = if (isLeft) borderLeftColor else borderRightColor,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        label = ""
    )

    val degrees by animateFloatAsState(
        targetValue = if (isLeft) 90f else 270f,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        label = "Infinite Colors"
    )

    Surface(
        modifier = modifier
            .clip(shape),
        shape = shape
    ) {
        Surface(
            color = backgroundColor,
            modifier = Modifier
                .padding(borderWidth)
                .drawWithContent {
                    rotate(degrees = degrees) {
                        drawCircle(
                            brush = borderColor.getBrushGradient(),
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            shape = shape
        ) {
            content()
        }
    }
}

fun Color.getBrushGradient() = Brush.sweepGradient(
    listOf(
        this.copy(alpha = .7F),
        this.copy(alpha = .05F)
    ),
)
