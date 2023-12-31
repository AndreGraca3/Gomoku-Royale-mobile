package pt.isel.gomoku.ui.components.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pt.isel.gomoku.R

const val AvatarTag = "MainScreenAvatarTag"
val userAvatar = SemanticsPropertyKey<String?>("UserAvatar")
var SemanticsPropertyReceiver.userAvatar by userAvatar

@Composable
fun AsyncAvatar(avatar: String? = null, size: Dp = 50.dp, onClick: () -> Unit = {}) {
    AsyncImage(
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .border(2.dp, Color.White, CircleShape)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .testTag(AvatarTag)
            .semantics { userAvatar = avatar },
        model = avatar ?: R.drawable.user_icon,
        contentDescription = null
    )
}