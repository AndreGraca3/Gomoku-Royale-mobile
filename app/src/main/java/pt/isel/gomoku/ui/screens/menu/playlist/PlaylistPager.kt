package pt.isel.gomoku.ui.screens.menu.playlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pt.isel.gomoku.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistCards(cards: List<Playlist>) {
    val pagerState = rememberPagerState(pageCount = { cards.size })

    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(500)
        isVisible = true
    }

    //AnimatedVisibility(visible = isVisible, enter = fadeIn() + scaleIn(), modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            pageSize = PageSize.Fill,
            contentPadding = PaddingValues(horizontal = 100.dp)
        ) { page ->
            MatchCard(id = cards[page].image, pagerState, page, onClick = cards[page].onClick)
        }
    //}
}