package pt.isel.gomoku.ui.screens.leaderboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.stats.UserRank

@Composable
fun LeaderBoardView(leaderBoard: List<UserRank>) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.wrapContentSize().scale(0.8F)
    ) {
        Image(
            painter = painterResource(id = R.drawable.leaderboard_paper),
            contentDescription = "PaperScroll",
            contentScale = ContentScale.FillHeight,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .width(200.dp)
                .height(450.dp)
        ) {
            Text("Leaderboard", color = Color(0x80000000), fontSize = 32.sp)

            leaderBoard.forEachIndexed { i, userStat ->
                PlayerRank(userStat, i)
            }
        }
    }
}