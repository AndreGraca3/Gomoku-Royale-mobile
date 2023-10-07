package pt.isel.gomoku.ui.screens.board

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.board.Board
import pt.isel.gomoku.domain.board.BoardRun
import pt.isel.gomoku.domain.game.Dot
import pt.isel.gomoku.utils.playSound

@Composable
fun BoardScreen(ctx: Context) {
    var internalBoard by remember { mutableStateOf<Board>(BoardRun(19)) }
    var selector by remember { mutableStateOf<Dot?>(null) }

    /*LaunchedEffect(key1 = Unit) {
        playSound(ctx, R.raw.background_music) // music stops after plays, why?
    }*/

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.board_texture),
        contentScale = ContentScale.FillBounds,
        contentDescription = null
    )

    BoardView(internalBoard, selector) { dot ->

        if(selector == null || dot != selector) {
            selector = dot
            playSound(ctx, R.raw.selector)
            return@BoardView
        }

        internalBoard = internalBoard.play(dot, internalBoard.turn)

        if (internalBoard !is BoardRun) {
            playSound(ctx, R.raw.place_piece_winner)
            return@BoardView
        }
        val sound = if (Math.random() < 0.5) R.raw.place_piece_1 else R.raw.place_piece_2
        playSound(ctx, sound)
        selector = null
    }
}