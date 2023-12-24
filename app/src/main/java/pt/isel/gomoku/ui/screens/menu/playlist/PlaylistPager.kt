package pt.isel.gomoku.ui.screens.menu.playlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.ui.components.common.LoadingDots

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistPager(
    isVisible: Boolean,
    isActive: Boolean,
    cards: List<Playlist>,
    modifier: Modifier = Modifier,
) {

    val pagerState = rememberPagerState(pageCount = { cards.size })

    if(!isVisible) LoadingDots(modifier = modifier.fillMaxSize(), message = "Loading User...")

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + scaleIn(),
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fill,
            contentPadding = PaddingValues(horizontal = 100.dp)
        ) { page ->
            PlaylistCard(
                id = if (isActive) cards[page].image else cards[page].disabledImage,
                pagerState,
                page,
                onClick = cards[page].onClick
            )
        }
    }
}