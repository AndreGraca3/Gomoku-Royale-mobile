package pt.isel.gomoku.ui.screens.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.domain.loading
import pt.isel.gomoku.ui.components.buttons.ScaledButton
import pt.isel.gomoku.ui.components.common.IOResourceLoader
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.bounceAnimation

const val MatchScreenTag = "MatchScreenTag"

@Composable
fun MatchScreen(
    screenState: MatchScreenState,
    users: List<UserPlayer?>,
    matchIOState: IOState<Match>,
    pendingPlayDot: Dot?,
    onCancelRequested: () -> Unit,
    onBackRequested: () -> Unit,
    onForfeitRequested: () -> Unit,
    onPlayRequested: (String, Dot) -> Unit,
) {
    GomokuTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .testTag(MatchScreenTag)
        ) {
            IOResourceLoader(
                loadingMessage = "Waiting for opponent...",
                loadingContent = {
                    ScaledButton(
                        text = "Cancel",
                        color = Color.Red,
                        modifier = Modifier.bounceAnimation(),
                        onClick = onCancelRequested
                    )
                },
                resource = if (screenState == MatchScreenState.WAITING_FOR_OPPONENT) loading() else matchIOState,
            ) { match ->
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.floor_background),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Floor background"
                )

                MatchView(
                    screenState,
                    users,
                    match,
                    pendingPlayDot = pendingPlayDot,
                    onBackRequested = onBackRequested,
                    onForfeitRequested = onForfeitRequested,
                    onCellClick = { dot ->
                        onPlayRequested(match.id, dot)
                    })
            }
        }
    }
}
