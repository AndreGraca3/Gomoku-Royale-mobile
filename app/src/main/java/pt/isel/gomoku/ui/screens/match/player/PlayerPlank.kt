package pt.isel.gomoku.ui.screens.match.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.ui.components.ElipsedText
import pt.isel.gomoku.ui.theme.driftWoodFamily

@Composable
fun RowScope.PlayerPlank(user: User, isSelected: Boolean) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .scale(0.9F)
            .fillMaxHeight()
            .weight(1F)
    ) {
        Image(
            painter = painterResource(id = R.drawable.wooden_plank),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Wooden plank",
            modifier = Modifier
                .fillMaxSize()
                .border(
                    2.dp,
                    if (isSelected) Color.Cyan
                    else Color.Transparent,
                    RoundedCornerShape(8.dp)
                )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Center)
        ) {

            AsyncImage(
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(40.dp)
                    .border(2.dp, Color(233, 104, 19, 255), CircleShape)
                    .clip(CircleShape),
                model = user.avatar ?: R.drawable.user_icon,
                contentDescription = null
            )

            PlayerDetails(user)
        }
    }
}