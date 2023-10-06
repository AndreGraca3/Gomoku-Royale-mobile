package pt.isel.gomoku.ui.screens.board

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.board.Board
import pt.isel.gomoku.domain.board.BoardRun
import pt.isel.gomoku.utils.playSound

@Composable
fun BoardScreen(ctx: Context) {
    var internalBoard by remember { mutableStateOf<Board>(BoardRun(15)) }

    BoardView(internalBoard) { dot ->
        internalBoard = internalBoard.play(dot, internalBoard.turn)
        playSound(ctx, R.raw.place_piece)
    }

}