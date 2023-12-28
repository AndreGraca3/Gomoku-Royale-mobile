package pt.isel.gomoku.ui.screens.leaderboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import pt.isel.gomoku.domain.stats.Rank
import pt.isel.gomoku.ui.components.text.TruncatedText
import pt.isel.gomoku.ui.theme.Bronze
import pt.isel.gomoku.ui.theme.Brown
import pt.isel.gomoku.ui.theme.Yellow
import pt.isel.gomoku.utils.innerShadow

@Composable
fun LeaderBoardPosition(
    position: Int,
    playerName: String,
    rank: Rank,
    onPlayerRequested: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    val color = when (position) {
        1 -> Yellow
        2 -> Color.Gray
        3 -> Bronze
        else -> Color.White
    }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100 * position.toLong())
        visible = true
    }

    AnimatedVisibility(visible = visible, enter = scaleIn()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .height(45.dp)
                .clip(shape)
                .background(Brown)
                .innerShadow(Color.White, cornersRadius = 16.dp, spread = 4.dp, blur = 4.dp)
                .clickable { onPlayerRequested() }
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8F)
            ) {
                TruncatedText(
                    "#${position}. $playerName",
                    color = color,
                    fontSize = TextUnit.Unspecified
                )
            }
            AsyncImage(
                model = rank.iconUrl,
                contentDescription = "Rank",
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}