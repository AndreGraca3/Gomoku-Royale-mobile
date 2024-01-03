package pt.isel.gomoku.ui.screens.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.screens.menu.actions.MenuActions
import pt.isel.gomoku.ui.screens.menu.logo.HeartBeatLogo
import pt.isel.gomoku.ui.screens.menu.playlist.Playlist
import pt.isel.gomoku.ui.screens.menu.playlist.PlaylistPager

const val PlayCardsTag = "PlayCards"
const val MultiplayerCardTag = "MultiplayerCardTag"
const val PrivateCardTag = "PrivateCardTag"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBody(
    innerPadding: PaddingValues,
    isInitialized: Boolean,
    isLoggedIn: Boolean,
    onMatchRequested: (Boolean) -> Unit,
    onLeaderBoardRequested: () -> Unit,
    onAboutRequested: () -> Unit,
    onStatsRequested: () -> Unit
) {
    val playlistCards = listOf(
        Playlist(
            name = "Multiplayer",
            image = R.drawable.multiplayer_match,
            disabledImage = R.drawable.multiplayer_match_disabled,
            onClick = { onMatchRequested(false) },
            testTag = MultiplayerCardTag
        ),
        Playlist(
            name = "Private",
            image = R.drawable.private_match,
            disabledImage = R.drawable.private_match_disabled,
            onClick = { onMatchRequested(true) },
            testTag = PrivateCardTag
        ),
    )

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier.padding(innerPadding),
        topBar = {
            HeartBeatLogo(innerPadding, modifier = Modifier.fillMaxWidth())
        },
        bottomBar = {
            MenuActions(
                onLeaderBoardRequested,
                onAboutRequested,
                onStatsRequested
            )
        }
    ) {
        PlaylistPager(
            isInitialized,
            isActive = isLoggedIn,
            cards = playlistCards,
            modifier = Modifier
                .padding(it)
                .testTag(PlayCardsTag)
        )
    }
}