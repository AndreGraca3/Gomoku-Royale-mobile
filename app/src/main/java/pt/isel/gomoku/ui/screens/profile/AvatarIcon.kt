package pt.isel.gomoku.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.ui.components.common.AsyncAvatar

@Composable
fun AvatarIcon(
    avatar: String?,
    role: String,
    onAvatarChange: (String?) -> Unit
) {
    Box {
        AsyncAvatar(
            avatar = avatar,
            size = 150.dp
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
