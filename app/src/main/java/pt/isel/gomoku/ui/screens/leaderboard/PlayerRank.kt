package pt.isel.gomoku.ui.screens.leaderboard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import pt.isel.gomoku.domain.stats.UserRank
import pt.isel.gomoku.ui.components.text.TruncatedText
import pt.isel.gomoku.ui.theme.handFont

@Composable
fun PlayerRank(userStat: UserRank, i: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
    ) {
        Column(
            modifier = Modifier.widthIn(0.dp, 100.dp)
        ) {
            TruncatedText(
                text = "${i + 1}. ${userStat.userName}",
                fontFamily = handFont,
                fontSize = 16.sp,
                color = Color(0x81000000)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Red),
        ) {
            AsyncImage(
                model = userStat.rankImage,
                contentDescription = "Rank",
                contentScale = ContentScale.Inside,
            )
        }
    }
}