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
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.http.model.stats.LeaderBoard
import pt.isel.gomoku.ui.components.common.LoadingDots

@Composable
fun LeaderBoardView(leaderBoard: IOState<LeaderBoard>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (leaderBoard is Loading) {
            LoadingDots()
        }

        leaderBoard.getOrNull()?.ranks?.forEachIndexed { i, it ->
            LeaderBoardPosition(
                position = i + 1,
                playerName = it.name,
                rank = it.rank,
                onPlayerRequested = { /*TODO*/ }
            )
        }
    }
}