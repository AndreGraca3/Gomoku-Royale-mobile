package pt.isel.gomoku.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer
import pt.isel.gomoku.R
import pt.isel.gomoku.utils.animateScaleWithDelay

@Composable
fun LoadingDots(
    modifier: Modifier = Modifier,
    message: String = "Loading..."
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(5) {
                val scale by animateScaleWithDelay(delay = it * 100, duration = 2000)
                Image(
                    painter = painterResource(id = R.drawable.black_piece),
                    contentDescription = "spinner",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.scale(scale)
                )
            }
        }
        Text(
            text = message,
            modifier = Modifier.shimmer(),
        )
    }
}