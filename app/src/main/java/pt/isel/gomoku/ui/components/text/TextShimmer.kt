package pt.isel.gomoku.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.shimmer
import pt.isel.gomoku.ui.theme.mainFont

private val titleTheme = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = 2000,
            delayMillis = 3000,
            easing = LinearEasing,
        ),
    ),
    blendMode = BlendMode.DstIn,
    shimmerWidth = 40.dp,
    rotation = 45f,
    shaderColors = listOf(
        Color.White.copy(alpha = 0.6F),
        Color.White.copy(alpha = 1F),
        Color.White.copy(alpha = 0.6F),
    ),
    shaderColorStops = null
)

@Composable
fun TextShimmer(text: String, fontFamily: FontFamily = mainFont) {
    CompositionLocalProvider(
        LocalShimmerTheme provides titleTheme,
    ) {
        Text(
            text,
            fontFamily = fontFamily,
            fontSize = 24.sp,
            modifier = Modifier.shimmer()
        )
    }
}