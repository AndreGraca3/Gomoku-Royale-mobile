package pt.isel.gomoku.ui.screens.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.http.model.UserStatsOutputModel
import pt.isel.gomoku.ui.components.common.IOResourceLoader
import pt.isel.gomoku.ui.components.layouts.RoundedLayout
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.getRankIconByName

@Composable
fun StatsScreen(
    userStats: IOState<UserStatsOutputModel>
) {
    GomokuTheme(background = R.drawable.grid_background) {
        RoundedLayout {
            IOResourceLoader(resource = userStats) {
                val winStats = it.winStats
                val matchesStats = it.matchesStats

                Text(
                    text = "Your Stats",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )

                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "\uD83C\uDFC6 Win Stats \uD83C\uDFC6",
                            color = Color.White
                        )
                        Text(text = "📈 Wins: ${winStats.totalWins}")
                        Text(text = "\uD83D\uDCC9 Loses: ${winStats.loses}")
                        Text(text = "\uD83E\uDD1D Draws: ${winStats.draws}")
                        Text(text = "\uD83D\uDCC8⚫ Wins as black: ${winStats.winsAsBlack}")
                        Text(text = "\uD83D\uDCC8⚪ Wins as white: ${winStats.winsAsWhite}")
                        Text(text = "\uD83D\uDCCA Win rate: ${winStats.winRate}")
                    }
                }

                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "\uD83D\uDCCA Matches Stats \uD83D\uDCCA",
                            color = Color.White
                        )
                        Text(text = "\uD83D\uDCDD Total matches: ${matchesStats.totalMatches}")
                        Text(text = "⚫ Matches as black: ${matchesStats.matchesAsBlack}")
                        Text(text = "⚪ Matches as white: ${matchesStats.matchesAsWhite}")
                    }
                }

                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painter = painterResource(getRankIconByName(it.rank.name)),
                            contentDescription = "rank"
                        )
                    }
                }
            }
        }
    }
}