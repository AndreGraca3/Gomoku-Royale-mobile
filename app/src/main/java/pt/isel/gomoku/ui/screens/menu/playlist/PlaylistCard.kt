package pt.isel.gomoku.ui.screens.menu.playlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import pt.isel.gomoku.R
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchCard(id: Int, pagerState: PagerState, page: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center,
        modifier = Modifier
            .size(180.dp)
            .graphicsLayer {
                val pageOffset = (
                        (pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue
                val alphaLerp = lerp(
                    start = 0.4f,
                    stop = 1f,
                    1f - pageOffset.coerceIn(0f, 1f)
                )
                val scaleLerp = lerp(
                    start = 0.2f,
                    stop = 1f,
                    1f - pageOffset.coerceIn(0f, .7f)
                )
                alpha = alphaLerp
                scaleX = scaleLerp
                scaleY = scaleLerp
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick()
                    }
                )
            }
    )
}