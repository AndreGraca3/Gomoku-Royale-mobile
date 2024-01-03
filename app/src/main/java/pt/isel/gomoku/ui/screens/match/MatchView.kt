package pt.isel.gomoku.ui.screens.match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.http.model.MatchState
import pt.isel.gomoku.ui.components.buttons.AnimatedImageButton
import pt.isel.gomoku.ui.components.buttons.ScaledButton
import pt.isel.gomoku.ui.screens.match.board.BoardView
import pt.isel.gomoku.ui.screens.match.player.PlayerPlankRow

@Composable
fun MatchView(
    screenState: MatchScreenState,
    users: List<UserPlayer?>,
    match: Match,
    pendingPlayDot: Dot?,
    onBackRequested: () -> Unit,
    onForfeitRequested: () -> Unit,
    onCellClick: (Dot) -> Unit,
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
            if (match.state == MatchState.ONGOING) {
                ScaledButton(
                    text = "Forfeit",
                    color = Color.Red,
                    enabled = screenState != MatchScreenState.FORFEITING,
                    shape = RoundedCornerShape(6.dp),
                    onClick = onForfeitRequested,
                    modifier = Modifier.padding(8.dp)
                )
            }
            AnimatedImageButton(R.drawable.settings_button, 48.dp)
        }

        val isMyTurn = users[0]?.player == match.board.turn

        PlayerPlankRow(users.map { it?.user }, isMyTurn)
        BoardView(match.board, pendingPlayDot, isMyTurn, onCellClick)
        Spacer(modifier = Modifier.padding(8.dp))
    }
}