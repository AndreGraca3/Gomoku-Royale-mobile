package pt.isel.gomoku.ui.screens.menu.playlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistCards(cards: List<Playlist>) {
    val pagerState = rememberPagerState(pageCount = { cards.size })

    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        state = pagerState,
        pageSize = PageSize.Fill,
        contentPadding = PaddingValues(horizontal = 100.dp)
    ) { page ->
        MatchCard(id = cards[page].image, pagerState, page, onClick = cards[page].onClick)
    }
}