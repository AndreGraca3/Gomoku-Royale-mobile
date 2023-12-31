package pt.isel.gomoku.ui.screens.match

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.http.model.PlayOutputModel
import pt.isel.gomoku.ui.components.buttons.ScaledButton
import pt.isel.gomoku.ui.components.common.IOResourceLoader
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.playSound

@Composable
fun MatchScreen(
    users: List<UserPlayer?>,
    matchIOState: IOState<Match>,
    pendingPlay: IOState<PlayOutputModel>,
    onPlayRequested: (String, Dot) -> Unit,
    onBackRequested: () -> Unit = {},
) {
    GomokuTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            IOResourceLoader(
                loadingMessage = "Waiting for opponent...",
                loadingContent = {
                    // animate infinitely a scale for scale button
                    val transition = rememberInfiniteTransition(label = "")
                    val scale by transition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        ), label = ""
                    )
                    ScaledButton(
                        text = "Cancel",
                        color = Color.Red,
                        modifier = Modifier.scale(scale),
                        onClick = onBackRequested
                    )
                },
                resource = matchIOState,
            ) { match ->
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.floor_background),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Floor background"
                )

                MatchView(
                    users,
                    match,
                    pendingPlay = pendingPlay,
                    onBackRequested = onBackRequested,
                    onCellClick = { dot ->
                        onPlayRequested(match.id, dot)
                    })
            }
        }
    }
}
