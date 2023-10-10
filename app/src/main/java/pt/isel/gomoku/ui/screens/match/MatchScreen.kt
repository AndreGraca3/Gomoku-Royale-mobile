package pt.isel.gomoku.ui.screens.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.Match
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.domain.board.BoardRun
import pt.isel.gomoku.ui.components.AnimatedImageButton
import pt.isel.gomoku.ui.screens.match.board.BoardView
import pt.isel.gomoku.ui.screens.match.player.PlayerPlankRow
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.playSound

@Composable
fun MatchScreen(onBackRequested: () -> Unit = {}) { // TODO: this will receive match object with view model

    val ctx = LocalContext.current

    // this will be moved to view model
    val users = listOf(
        User("Andre dos Graças", null, "Grand Champion"),
        User("Diogo Maça Pereira dos Santos", null, "Silver"),
    )

    val match = Match(
        "test-match",
        "public",
        users[0],
        users[1]
    )

    var internalMatch by remember {
        mutableStateOf(
            match
        )
    }

    /*LaunchedEffect(key1 = Unit) {
        playSound(ctx, R.raw.background_music) // music stops after plays, why?
    }*/

    GomokuTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.floor_background),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Floor background"
            )

            // TODO: move to MatchView.kt?
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(22.dp),
                modifier = Modifier.padding(10.dp)
            ) {

                PlayerPlankRow(users, internalMatch.board.turn)

                BoardView(internalMatch.board) { dot ->

                    internalMatch = internalMatch.play(dot, internalMatch.board.turn)

                    if (internalMatch.board !is BoardRun) {
                        playSound(ctx, R.raw.place_piece_winner)
                        return@BoardView
                    }
                    val sound =
                        if (Math.random() < 0.5) R.raw.place_piece_1 else R.raw.place_piece_2
                    playSound(ctx, sound)
                }

                AnimatedImageButton(R.drawable.home_button, 60.dp, onBackRequested)
            }
        }
    }
}

@Preview
@Composable
fun MatchScreenPreview() {
    MatchScreen()
}