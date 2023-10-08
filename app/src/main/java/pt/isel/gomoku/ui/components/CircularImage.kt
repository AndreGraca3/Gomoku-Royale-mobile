package pt.isel.gomoku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CircularImage(id: Int, size: Int = 40) {
    Image(
        painter = painterResource(id = id),
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape),
        contentDescription = null
    )
}