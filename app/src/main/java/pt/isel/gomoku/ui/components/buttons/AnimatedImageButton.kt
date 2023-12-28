package pt.isel.gomoku.ui.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.utils.bounceAnimation
import pt.isel.gomoku.utils.bounceClick
import pt.isel.gomoku.utils.playSound

@Composable
fun AnimatedImageButton(
    id: Int,
    size: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val ctx = LocalContext.current
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
                .bounceClick(
                    onClickIn = {
                        ctx.playSound(R.raw.wooden_click_in_1)
                    },
                    onClickOut = {
                        ctx.playSound(R.raw.wooden_click_out_1)
                        onClick()
                    }
                )
        )
    }
}
