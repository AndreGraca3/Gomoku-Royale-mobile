package pt.isel.gomoku.ui.components.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.utils.playSound

@Composable
fun AnimatedImageButton(id: Int, size: Dp, onClick: () -> Unit = {}) {
    val ctx = LocalContext.current

    var selected by remember { mutableStateOf(false) }
    val scale = animateFloatAsState(
        if (selected) 0.8f else 1f,
        if (selected) tween(durationMillis = 0) else tween(200),
        label = ""
    )

    Image(
        contentScale = ContentScale.Fit,
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .scale(scale.value)
            .clip(RoundedCornerShape(4.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        ctx.playSound(R.raw.wooden_click_in_1)
                        selected = true
                        try {
                            awaitRelease()
                            ctx.playSound(R.raw.wooden_click_out_1)
                            onClick()
                        } finally {
                            selected = false
                        }
                    }
                )
            }
    )
}
