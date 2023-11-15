package pt.isel.gomoku.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.screens.menu.logo.HeartBeatLogo
import pt.isel.gomoku.ui.screens.menu.playlist.Playlist
import pt.isel.gomoku.ui.screens.menu.playlist.PlaylistCards
import pt.isel.gomoku.utils.playSound

@Composable
fun MenuBody(onMatchRequested: () -> Unit) {
    val ctx = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(34.dp)
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(3) {
                    Button({}) {
                        Text("button")
                    }
                }
            }
        }
    }
}