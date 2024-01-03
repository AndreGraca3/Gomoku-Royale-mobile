package pt.isel.gomoku.ui.screens.match.player

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.ui.components.common.AsyncAvatar
import pt.isel.gomoku.ui.components.common.LoadingDots

@Composable
fun RowScope.PlayerPlank(user: User?, isSelected: Boolean) {
    val scale by animateFloatAsState(
        if (isSelected) 0.9F else 0.7F,
        animationSpec = tween(1000),
        label = "scale"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .scale(scale)
            .fillMaxWidth(0.7F)
            .fillMaxHeight(0.8F)
            .weight(1F)
    ) {
        Image(
            painter = painterResource(id = R.drawable.wooden_plank),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Wooden plank",
            modifier = Modifier.fillMaxSize()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Center)
        ) {

            if (user == null) {
                LoadingDots()
                return
            }

            AsyncAvatar(user.avatar, 40.dp)
            PlayerDetails(user)
        }
    }
}