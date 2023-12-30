package pt.isel.gomoku.ui.screens.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.ui.components.common.IOResourceLoader
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.playSound

@Composable
fun MatchScreen(
    users: List<User?>,
    match: IOState<Match>,
    onPlayRequested: (String, Dot) -> Unit,
    onCancelRequested: () -> Unit = {}
) {
    val ctx = LocalContext.current
    GomokuTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            IOResourceLoader(
                resource = match,
                onCancelRequested = onCancelRequested
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.floor_background),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Floor background"
                )

                MatchView(
                    users,
                    it,
                    onBackRequested = onCancelRequested,
                    onCellClick = { dot ->
                        onPlayRequested(it.id, dot)
                        //internalMatch = internalMatch.play(dot, internalMatch.board.turn)
                        //if (internalMatch.board !is BoardRun) {
                        //    ctx.playSound(R.raw.place_piece_winner)
                        //    return@MatchView
                        //}
                        val sound =
                            if (Math.random() < 0.5) R.raw.place_piece_1 else R.raw.place_piece_2
                        ctx.playSound(sound)
                    })
            }
        }
    }
}

//@Preview
//@Composable
//fun MatchScreenPreview() {
//    MatchScreen()
//}