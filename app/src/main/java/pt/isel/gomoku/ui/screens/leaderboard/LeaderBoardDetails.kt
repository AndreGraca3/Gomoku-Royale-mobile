package pt.isel.gomoku.ui.screens.leaderboard

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.domain.LoadState
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.exceptionOrNull
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.stats.UserRank
import pt.isel.gomoku.ui.components.LoadingSpinner

@Composable
fun LeaderBoardDetails(leaderBoard: LoadState<List<UserRank>>) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth(0.5F)
    ) {
        if (leaderBoard is Loading) {
            LoadingSpinner()
        }

        leaderBoard.exceptionOrNull()?.let {
            Toast.makeText(
                LocalContext.current,
                "Something went wrong: ${it.message}",
                Toast.LENGTH_LONG
            ).show()
        }

        leaderBoard.getOrNull()?.forEachIndexed { i, userStat ->
            PlayerRank(userStat, i)
        }
    }
}