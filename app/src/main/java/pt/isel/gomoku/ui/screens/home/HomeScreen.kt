package pt.isel.gomoku.ui.screens.home

import android.app.Activity
import android.content.Context
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.finishAffinity
import pt.isel.gomoku.R
import pt.isel.gomoku.model.Move
import pt.isel.gomoku.model.Player
import pt.isel.gomoku.ui.components.RoundButton
import pt.isel.gomoku.utils.playSound

@Composable
fun HomeScreen(ctx: Context) {
    val activity = (LocalContext.current as? Activity) /** Está correto? **/


    var moves by remember { mutableStateOf(listOf<Move>()) }
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "scale"
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Gomoku",
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    transformOrigin = TransformOrigin.Center
                },
            // Text composable does not take TextMotion as a parameter.
            // Provide it via style argument but make sure that we are copying from current theme
            style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
        )


        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            MatchCard(id = R.drawable.multiplayer_match) {
                playSound(ctx, R.raw.click)
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            MatchCard(id = R.drawable.private_match) {
                playSound(ctx, R.raw.click)
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            RoundButton(
                onClick = {},
            ) {
                Text("Settings")
            }

        }

        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            RoundButton(
                onClick = {
                    activity?.finish()
                }
            ) {
                Text("Exit")
            }
        }
    }
}


/*Row {
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
})*/

@Composable
fun Title() {
    Text("Gomoku")
}