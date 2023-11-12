package pt.isel.gomoku.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pt.isel.gomoku.R

@Composable
fun Avatar(avatar: String? = null, size: Dp = 40.dp) {
    AsyncImage(
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(40.dp)
            .border(2.dp, Color(233, 104, 19, 255), CircleShape)
            .clip(CircleShape),
        model = avatar ?: R.drawable.user_icon,
        contentDescription = null
    )
}