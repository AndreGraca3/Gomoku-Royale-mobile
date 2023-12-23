package pt.isel.gomoku.ui.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.utils.bounceAnimation
import pt.isel.gomoku.utils.bounceClick

@Composable
fun AnimatedImageButton(
    id: Int,
    size: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.bounceAnimation()
    ) {
        Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(id = id),
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(4.dp))
                .bounceClick(onClick)
        )
    }
}
