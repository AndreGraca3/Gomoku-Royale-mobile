package pt.isel.gomoku.ui.components.text

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.shimmer
import pt.isel.gomoku.ui.theme.mainFont

private val titleTheme = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = 1500,
            delayMillis = 2000,
            easing = LinearEasing,
        ),
    ),
    blendMode = BlendMode.DstIn,
    shimmerWidth = 40.dp,
    rotation = 45F,
    shaderColors = listOf(
        Color.White.copy(alpha = 0.7F),
        Color.White.copy(alpha = 1F),
        Color.White.copy(alpha = 0.7F),
    ),
    shaderColorStops = null
)

@Composable
fun TextShimmer(
    text: String,
    color: Color = Color.White,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily = mainFont,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    CompositionLocalProvider(
        LocalShimmerTheme provides titleTheme,
    ) {
        Text(
            text,
            fontFamily = fontFamily,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            modifier = Modifier.shimmer()
        )
    }
}