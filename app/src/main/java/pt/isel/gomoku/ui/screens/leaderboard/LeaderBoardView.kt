package pt.isel.gomoku.ui.screens.leaderboard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.LoadState
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.exceptionOrNull
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.stats.UserRank
import pt.isel.gomoku.ui.components.LoadingSpinner

@Composable
fun LeaderBoardView(leaderBoard: LoadState<List<UserRank>>) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxHeight(0.9F)
    ) {
        Image(
            painter = painterResource(id = R.drawable.leaderboard_paper),
            contentDescription = "PaperScroll",
            contentScale = ContentScale.FillHeight,
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxHeight(0.7F)
        ) {
            Text("Leaderboard", color = Color(0x80000000), fontSize = 32.sp)

            LeaderBoardDetails(leaderBoard)
        }
    }
}