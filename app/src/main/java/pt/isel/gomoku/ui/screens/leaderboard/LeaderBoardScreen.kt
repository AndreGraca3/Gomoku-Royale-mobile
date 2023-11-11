package pt.isel.gomoku.ui.screens.leaderboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.stats.UserStats

@Composable
fun LeaderBoardScreen() {
    val leaderBoard = listOf(
        UserStats(
            3,
            "Andre",
            "Grand Champion",
            R.drawable.grand_champion
        ),
        UserStats(
            1,
            "Daniel",
            "Bronze",
            R.drawable.silver
        ),
        UserStats(
            2,
            "Diogo",
            "Silver",
            R.drawable.silver
        ),
        UserStats(
            5,
            "João",
            "Bronze",
            R.drawable.silver
        ),
    )

    Column {
        for (userStat in leaderBoard) {
            RankRow(
                position = leaderBoard.indexOf(userStat) + 1,
                idUser = userStat.id,
                playerName = userStat.userName,
                rankImage = userStat.rankImage
            )
        }
    }
}


@Composable
fun RankRow(position: Int, idUser: Int, playerName: String, rankImage: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.wooden_plank)
            )
            .clickable {
                // navigate to ...
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.size(75.dp)
        ) {
            Text(
                text = position.toString()
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.size(75.dp)
        ) {
            Text(
                text = playerName
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.size(75.dp)
        ) {
            Image(
                painterResource(id = rankImage),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun LeaderBoardPreview() {
    LeaderBoardScreen()
}