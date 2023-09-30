package pt.isel.gomoku.ui.screens.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.model.Move
import pt.isel.gomoku.model.Player
import pt.isel.gomoku.ui.components.RoundButton
import pt.isel.gomoku.utils.playSound

@Composable
fun HomeScreen(ctx: Context) {
    var moves by remember { mutableStateOf(listOf<Move>()) }

    Column(
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MatchCard(id = R.drawable.multiplayer_match) {
                playSound(ctx, R.raw.click)
            }
            MatchCard(id = R.drawable.private_match) {
                playSound(ctx, R.raw.click)
            }
        }

        Row {
            moves.map {
                Image(
                    painter = painterResource(if (it.player == Player.BLACK) R.drawable.black_piece else R.drawable.white_piece),
                    contentDescription = null
                )
            }
        }

        RoundButton(onClick = {
            playSound(ctx, R.raw.place_piece)
            moves = moves + Move(Player.values().random())
        }, content = {
            Text("Click")
        })
    }
}