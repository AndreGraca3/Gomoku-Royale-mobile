package pt.isel.gomoku.ui.screens.leaderboard

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.LoadState
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.exceptionOrNull
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.stats.UserRank
import pt.isel.gomoku.domain.success
import pt.isel.gomoku.ui.components.LoadingSpinner
import pt.isel.gomoku.ui.components.buttons.AnimatedImageButton
import pt.isel.gomoku.ui.screens.menu.topbar.MenuTopBar
import pt.isel.gomoku.ui.theme.GomokuTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                leaderBoard.getOrNull()?.let {
                    LeaderBoardView(leaderBoard = it)
                }

                if(leaderBoard is Loading) {
                    LoadingSpinner()
                }

                leaderBoard.exceptionOrNull()?.let {
                    Toast.makeText(
                        LocalContext.current,
                        "Something went wrong: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

@Preview
@Composable
fun LeaderBoardScreenPreview() {
    LeaderBoardScreen(
        leaderBoard = success(
            listOf(
                UserRank(1, "André", "Grand Champion"),
                UserRank(1, "Diogo Maça Pereira dos Santos Ferreira mil", "Silver"),
            )
        )
    )
}