package pt.isel.gomoku.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R

@Composable
fun AvatarIcon(
    role: String,
    onAvatarChange: (String?) -> Unit
) {
    Box {
        Image(
            painter = painterResource(R.drawable.diogo_avatar),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(150.dp)
                .border(2.dp, Color.White, CircleShape)
                .clip(CircleShape)
        )
        if (role == "admin") {
            Text(
                text = "👑",
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
        Text(
            text = "\uD83D\uDCF7",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clickable {
                    onAvatarChange(null)
                }
        )
    }
}
