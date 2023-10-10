package pt.isel.gomoku.ui.screens.match.player

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.domain.Player
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.ui.components.AnimatedBorderCard

@Composable
fun PlayerPlankRow(users: List<User>, turn: Player) {
    val shape = RoundedCornerShape(16.dp)

    AnimatedBorderCard(isLeft = (turn == Player.BLACK), shape = shape) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(shape)
                .background(Color(36, 20, 0, 255).copy(alpha = 0.6F))
                .border(1.dp, Color.Black, shape)
                .drawBehind {
                    drawLine(
                        color = Color.Black,
                        start = Offset(x = size.width / 2F, y = size.height),
                        end = Offset(x = size.width / 2F, y = 0F),
                        strokeWidth = 4F
                    )
                }
        ) {
            for (i in users.indices) {
                this.PlayerPlank(users[i])
            }
        }
    }
}