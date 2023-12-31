package pt.isel.gomoku.utils

import android.graphics.BlurMaskFilter
import android.graphics.Outline
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

enum class PressState { Pressed, Idle }

/**
 * A modifier that makes a composable clickable with a bounce animation. Only works if click wasn't consumed by a parent.
 */
fun Modifier.bounceClick(
    onClickIn: () -> Unit = {},
    onClickOut: () -> Unit = {},
    onCancel: () -> Unit = {}
) = composed {
    var pressState by remember { mutableStateOf(PressState.Idle) }
    val scale by animateFloatAsState(
        if (pressState == PressState.Pressed) 0.8F else 1F,
        label = "scale"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(pressState) {
            awaitPointerEventScope {
                if (pressState == PressState.Pressed) {
                    val upGesture = waitForUpOrCancellation()
                    pressState = PressState.Idle
                    if (upGesture != null) onClickOut()
                    else onCancel()
                } else {
                    awaitFirstDown(false)
                    pressState = PressState.Pressed
                    onClickIn()
                }
            }
        }
}

fun Modifier.bounceAnimation() = composed {
    val transition = rememberInfiniteTransition(label = "bounce")

    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(
            durationMillis = (500..1000).random(),
            easing = LinearEasing
        ),
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
    this
        .offset(y = offsetY.dp)
        .scale(scaleX = bounceScale, scaleY = 1F)
}

fun Modifier.spin(duration: Int = 1000, delay: Int = 0): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "spin")

    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(
            durationMillis = duration,
            delayMillis = delay,
            easing = EaseInOut
        ),
        repeatMode = RepeatMode.Restart
    )

    val rotation by transition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = animationSpec,
        label = "rotation"
    )

    this.graphicsLayer(rotationZ = rotation)
}

fun Modifier.innerShadow(
    color: Color = Color.Black,
    cornersRadius: Dp = 0.dp,
    spread: Dp = 0.dp,
    blur: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawWithContent {

    drawContent()

    val rect = Rect(Offset.Zero, size)
    val paint = Paint()

    drawIntoCanvas {

        paint.color = color
        paint.isAntiAlias = true
        it.saveLayer(rect, paint)
        it.drawRoundRect(
            left = rect.left,
            top = rect.top,
            right = rect.right,
            bottom = rect.bottom,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        if (blur.toPx() > 0) {
            frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
        val left = if (offsetX > 0.dp) {
            rect.left + offsetX.toPx()
        } else {
            rect.left
        }
        val top = if (offsetY > 0.dp) {
            rect.top + offsetY.toPx()
        } else {
            rect.top
        }
        val right = if (offsetX < 0.dp) {
            rect.right + offsetX.toPx()
        } else {
            rect.right
        }
        val bottom = if (offsetY < 0.dp) {
            rect.bottom + offsetY.toPx()
        } else {
            rect.bottom
        }
        paint.color = Color.Black
        it.drawRoundRect(
            left = left + spread.toPx() / 2,
            top = top + spread.toPx() / 2,
            right = right - spread.toPx() / 2,
            bottom = bottom - spread.toPx() / 2,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
        frameworkPaint.xfermode = null
        frameworkPaint.maskFilter = null
    }
}

fun Modifier.clickableWithoutRipple(onClick: () -> Unit) = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}

@Composable
fun animateScaleWithDelay(delay: Int, duration: Int) =
    rememberInfiniteTransition(label = "ScaledDelay").animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 300 * 4
                0f at delay with LinearEasing
                1f at delay + 300 with LinearEasing
                0f at delay + 300 * 2
            }
        ), label = "scaledDelay"
    )