package pt.isel.gomoku.ui.screens.match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.http.model.PlayOutputModel
import pt.isel.gomoku.ui.components.buttons.AnimatedImageButton
import pt.isel.gomoku.ui.screens.match.board.BoardView
import pt.isel.gomoku.ui.screens.match.player.PlayerPlankRow

@Composable
fun MatchView(
    users: List<UserPlayer?>,
    match: Match,
    pendingPlay: IOState<PlayOutputModel>,
    onCellClick: (Dot) -> Unit,
    onBackRequested: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(22.dp),
        modifier = Modifier.padding(8.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            AnimatedImageButton(R.drawable.home_button, 48.dp, onClick = onBackRequested)
            AnimatedImageButton(R.drawable.settings_button, 48.dp)
        }

        PlayerPlankRow(users.map { it?.user }, users[0]?.player == match.board.turn)
        BoardView(match.board, pendingPlay, users[0]?.player == match.board.turn, onCellClick)
        Spacer(modifier = Modifier.padding(8.dp))
    }
}