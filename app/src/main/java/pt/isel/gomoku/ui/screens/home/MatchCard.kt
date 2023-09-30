package pt.isel.gomoku.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.utils.noRippleClickable

@Composable
fun MatchCard(id: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(160.dp)
            .noRippleClickable { onClick() }
    )
}