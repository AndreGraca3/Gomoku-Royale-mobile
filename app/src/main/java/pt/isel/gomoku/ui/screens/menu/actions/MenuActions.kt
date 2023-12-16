package pt.isel.gomoku.ui.screens.menu.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.buttons.AnimatedImageButton

@Composable
fun MenuActions(onLeaderBoardRequested: () -> Unit, onAboutRequested: () -> Unit) {

    val actions = listOf(
        Action("Leaderboard", R.drawable.leaderboard_button, onClick = onLeaderBoardRequested),
        Action("Stats", R.drawable.stats_button, onClick = {}),
        Action("Settings", R.drawable.settings_button, onClick = {}),
        Action("Info", R.drawable.info_button, onClick = onAboutRequested),
    )

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(0.dp, 70.dp)
            .fillMaxWidth()
    ) {
        actions.forEach { action ->
            AnimatedImageButton(id = action.icon, size = 60.dp, onClick = action.onClick)
        }
    }
}