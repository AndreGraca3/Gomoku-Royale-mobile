package pt.isel.gomoku.ui.screens.match.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.ui.components.text.TruncatedText
import pt.isel.gomoku.ui.theme.woodFont
import pt.isel.gomoku.utils.getRankIconByName

@Composable
fun PlayerDetails(user: User?) {
    Column {
        TruncatedText(text = user?.name ?: "unknown", fontFamily = woodFont)
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Image(
                painterResource(id = getRankIconByName(user?.rank ?: "Bronze")),
                contentDescription = null,
                contentScale = ContentScale.Inside
            )
            TruncatedText(text = user?.rank ?: "Bronze", 10.sp)
        }
    }
}