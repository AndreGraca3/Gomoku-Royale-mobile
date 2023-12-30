package pt.isel.gomoku.ui.screens.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.http.model.LeaderBoard
import pt.isel.gomoku.ui.components.common.IOResourceLoader

@Composable
fun LeaderBoardView(
    leaderBoard: IOState<LeaderBoard>,
    onPlayerRequested: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        IOResourceLoader(
            loadingMessage = "Getting the best players...",
            resource = leaderBoard
        ) { leaderBoardData ->
            leaderBoardData.ranks.forEachIndexed { i, it ->
                LeaderBoardPosition(
                    position = i + 1,
                    playerName = it.name,
                    rank = it.rank,
                    onPlayerRequested = { onPlayerRequested(it.id) }
                )
            }
        }
    }
}