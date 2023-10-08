package pt.isel.gomoku.ui.screens.match.player

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun PlayerPlankRow(users: List<User>, turn: Player) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(36, 20, 0, 255).copy(alpha = 0.6F))
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
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
            val isSelected = (i == 0 && turn == Player.BLACK) || (i == 1 && turn == Player.WHITE)
            this.PlayerPlank(users[i], isSelected = isSelected)
        }
    }
}