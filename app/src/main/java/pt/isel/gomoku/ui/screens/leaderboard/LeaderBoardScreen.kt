package pt.isel.gomoku.ui.screens.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.http.model.LeaderBoard
import pt.isel.gomoku.ui.components.buttons.AnimatedImageButton
import pt.isel.gomoku.ui.theme.GomokuTheme

const val LeaderBoardScreenTag = "LeaderBoardScreenTag"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderBoardScreen(
    leaderBoard: IOState<LeaderBoard>,
    onPlayerRequested: (Int) -> Unit
) {
    GomokuTheme {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .testTag(LeaderBoardScreenTag),
            topBar = {
                AnimatedImageButton(
                    id = R.drawable.left_arrow_button,
                    size = 60.dp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(innerPadding)
            ) {
                Text(
                    "Leaderboard",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.White
                )
                LeaderBoardView(
                    leaderBoard = leaderBoard,
                    onPlayerRequested = onPlayerRequested
                )
            }
        }
    }
}