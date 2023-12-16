package pt.isel.gomoku.ui.screens.menu.playlist

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistCard(id: Int, pagerState: PagerState, page: Int, onClick: () -> Unit) {
    val scale by animateFloatAsState(
        targetValue = if (pagerState.currentPage == page) 1F else 0.5F,
        animationSpec = spring(stiffness = Spring.StiffnessLow), label = "MatchCardScale"
    )

    Image(
        painter = painterResource(id),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                val pageOffset = (
                        (pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue
                val alphaLerp = lerp(
                    start = 0.4F,
                    stop = 1F,
                    1F - pageOffset.coerceIn(0F, 1F)
                )
                alpha = alphaLerp
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (pagerState.currentPage == page) onClick()
                    }
                )
            }
    )
}