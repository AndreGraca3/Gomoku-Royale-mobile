package pt.isel.gomoku.ui.screens.menu.topbar

import androidx.compose.material3.Text
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.shimmer
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.theme.driftWoodFamily

private val titleTheme = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = 1000,
            delayMillis = 3000,
            easing = LinearEasing,
        ),
    ),
    blendMode = BlendMode.DstIn,
    shimmerWidth = 40.dp,
    rotation = 45f,
    shaderColors = listOf(
        Color.White.copy(alpha = 0.7f),
        Color.White.copy(alpha = 1f),
        Color.White.copy(alpha = 0.7f),
    ),
    shaderColorStops = null
)

@Composable
fun TitleShimmer() {
    CompositionLocalProvider(
        LocalShimmerTheme provides titleTheme,
    ) {
        Text(
            getString(LocalContext.current, R.string.app_name),
            fontFamily = driftWoodFamily,
            modifier = Modifier.shimmer(),
        )
    }
}