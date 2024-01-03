package pt.isel.gomoku.ui.screens.match.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.ui.components.common.AnimatedBorderCard
import pt.isel.gomoku.ui.components.text.TruncatedText

@Composable
fun PlayerPlankRow(users: List<User?>, isMyTurn: Boolean) {
    val shape = RoundedCornerShape(16.dp)

    AnimatedBorderCard(
        isLeft = isMyTurn,
        shape = shape,
        borderLeftColor = Color.Cyan,
        borderRightColor = Color.Red,
        backgroundColor = Color(60, 36, 0, 255)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(shape)
        ) {
            PlayerPlank(users[0], isMyTurn)
            TruncatedText(text = "VS", fontSize = 20.sp, modifier = Modifier.weight(0.3F))
            PlayerPlank(users[1], !isMyTurn)
        }
    }
}