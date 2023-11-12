package pt.isel.gomoku.ui.screens.menu

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextMotion
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.buttons.RoundButton
import pt.isel.gomoku.utils.playSound
import androidx.compose.material3.Icon

@Composable
fun MenuBody(innerPadding: PaddingValues, onMatchRequested: () -> Unit) {
    val ctx = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            MatchCard(id = R.drawable.multiplayer_match) {
                ctx.playSound(R.raw.metal_click_1)
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            MatchCard(id = R.drawable.private_match) {
                ctx.playSound(R.raw.metal_click_1)
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
    }
}