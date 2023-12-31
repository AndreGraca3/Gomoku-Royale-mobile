package pt.isel.gomoku.ui.screens.menu.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.buttons.AnimatedImageButton

const val MainScreenLeaderboardButtonTag = "MainScreenLeaderboardButtonTag"
const val MainScreenStatsButtonTag = "MainScreenStatsButtonTag"
const val MainScreenSettingsButtonTag = "MainScreenSettingsButtonTag"
const val MainScreenInfoButtonTag = "MainScreenInfoButtonTag"

@Composable
fun MenuActions(
    onLeaderBoardRequested: () -> Unit,
    onAboutRequested: () -> Unit,
    onStatsRequested: () -> Unit
) {

    val actions = listOf(
        Action(
            "Leaderboard",
            R.drawable.leaderboard_button,
            onClick = onLeaderBoardRequested,
            MainScreenLeaderboardButtonTag
        ),
        Action(
            "Stats",
            R.drawable.stats_button,
            onClick = onStatsRequested,
            MainScreenStatsButtonTag
        ),
        Action(
            "Settings",
            R.drawable.settings_button,
            onClick = {},
            MainScreenSettingsButtonTag
        ),
        Action(
            "Info",
            R.drawable.info_button,
            onClick = onAboutRequested,
            MainScreenInfoButtonTag
        ),
    )

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(0.dp, 70.dp)
            .fillMaxWidth()
    ) {
        actions.forEach { action ->
            AnimatedImageButton(
                modifier = Modifier.testTag(action.testTag),
                id = action.icon,
                size = 60.dp,
                onClick = action.onClick
            )
        }
    }
}