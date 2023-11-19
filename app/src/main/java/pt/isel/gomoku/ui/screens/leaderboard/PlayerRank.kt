package pt.isel.gomoku.ui.screens.leaderboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.domain.stats.UserRank
import pt.isel.gomoku.ui.components.text.ElipsedText
import pt.isel.gomoku.ui.theme.mainFont
import pt.isel.gomoku.utils.getRankIconByName

@Composable
fun PlayerRank(userStat: UserRank, i: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.height(32.dp)
    ) {
        Column(
            modifier = Modifier.widthIn(0.dp, 150.dp)
        ) {
            ElipsedText(
                text = "${i + 1}. ${userStat.userName}",
                fontFamily = mainFont,
                fontSize = 16.sp
            )
        }

        Image(
            painter = painterResource(id = getRankIconByName(userStat.rank)),
            contentDescription = "Rank",
            contentScale = ContentScale.Inside,
        )
    }
}