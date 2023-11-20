package pt.isel.gomoku.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.screens.menu.actions.Action
import pt.isel.gomoku.ui.screens.menu.actions.MenuActions
import pt.isel.gomoku.ui.screens.menu.logo.HeartBeatLogo
import pt.isel.gomoku.ui.screens.menu.playlist.Playlist
import pt.isel.gomoku.ui.screens.menu.playlist.PlaylistCards
import pt.isel.gomoku.utils.playSound

@Composable
fun MenuBody(
    innerPadding: PaddingValues,
    onMatchRequested: () -> Unit,
    onLeaderBoardRequested: () -> Unit
) {
    val ctx = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        HeartBeatLogo()

        PlaylistCards(
            listOf(
                Playlist("Multiplayer", R.drawable.multiplayer_match, onClick = {}),
                Playlist(
                    "Private", R.drawable.private_match, onClick = {
                        ctx.playSound(R.raw.metal_click_1)
                        onMatchRequested()
                    }
                ),
            )
        )

        MenuActions(
            listOf(
                Action(
                    "Leaderboard",
                    R.drawable.leaderboard_button,
                    onClick = onLeaderBoardRequested
                ),
                Action("Stats", R.drawable.stats_button, onClick = {}),
                Action("Settings", R.drawable.settings_button, onClick = {}),
                Action("Info", R.drawable.info_button, onClick = {}),
            )
        )
    }
}