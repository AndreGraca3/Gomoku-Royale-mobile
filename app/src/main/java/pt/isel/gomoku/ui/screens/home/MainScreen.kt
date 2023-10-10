package pt.isel.gomoku.ui.screens.home

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextMotion
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.RoundButton
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.playSound

@Composable
fun MainScreen(onBackRequested: () -> Unit = {}, onMatchRequested: () -> Unit = {}) {

    val ctx = LocalContext.current

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "scale"
    )

    GomokuTheme {
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
                    playSound(ctx, R.raw.metal_click_1)
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                MatchCard(id = R.drawable.private_match) {
                    playSound(ctx, R.raw.metal_click_1)
                    onMatchRequested()
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
                    onClick = { onBackRequested }
                ) {
                    Text("Exit")
                }
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