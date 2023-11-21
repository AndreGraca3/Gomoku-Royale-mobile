package pt.isel.gomoku.ui.screens.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.domain.LoadState
import pt.isel.gomoku.domain.stats.UserRank
import pt.isel.gomoku.domain.success
import pt.isel.gomoku.ui.screens.menu.topbar.MenuTopBar
import pt.isel.gomoku.ui.theme.GomokuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderBoardScreen(leaderBoard: LoadState<List<UserRank>>) {
    GomokuTheme {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MenuTopBar()
            },
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LeaderBoardView(leaderBoard)
            }
        }
    }
}

@Preview
@Composable
fun LeaderBoardScreenPreview() {
    val list = buildList<UserRank> {
        repeat(10) {
            UserRank(
                1,
                "Andre",
                "Grand Champion",
                "https://i.imgur.com/JGtwTBw.png"
            )
        }
    }
    LeaderBoardScreen(leaderBoard = success(list))
}