package pt.isel.gomoku.ui.screens.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.screens.menu.actions.MenuActions
import pt.isel.gomoku.ui.screens.menu.logo.HeartBeatLogo
import pt.isel.gomoku.ui.screens.menu.playlist.Playlist
import pt.isel.gomoku.ui.screens.menu.playlist.PlaylistPager
import pt.isel.gomoku.utils.playSound

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBody(
    innerPadding: PaddingValues,
    isInitialized: Boolean,
    isLoggedIn: Boolean,
    onMatchRequested: () -> Unit,
    onLeaderBoardRequested: () -> Unit,
    onAboutRequested: () -> Unit,
    onStatsRequested: () -> Unit
) {
    val ctx = LocalContext.current

    val playlistCards = listOf(
        Playlist(
            name = "Multiplayer",
            image = R.drawable.multiplayer_match,
            disabledImage = R.drawable.multiplayer_match_disabled,
            onClick = {}),
        Playlist(
            name = "Private",
            image = R.drawable.private_match,
            disabledImage = R.drawable.private_match_disabled,
            onClick = {
                ctx.playSound(R.raw.metal_click_1)
                onMatchRequested()
            }
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
            modifier = Modifier.padding(it)
        )
    }
}