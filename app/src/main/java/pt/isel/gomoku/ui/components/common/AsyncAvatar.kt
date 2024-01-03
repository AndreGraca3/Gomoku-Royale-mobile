package pt.isel.gomoku.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.theme.DarkBrown

const val AvatarTag = "MainScreenAvatarTag"
val userAvatar = SemanticsPropertyKey<String?>("UserAvatar")
var SemanticsPropertyReceiver.userAvatar by userAvatar

@Composable
fun AsyncAvatar(
    avatar: String? = null,
    size: Dp = 50.dp,
    isLoading: Boolean = false,
    onClick: (() -> Unit)? = null,
) {

    val modifier = Modifier
        .size(size)
        .border(2.dp, Color.White, CircleShape)
        .clip(CircleShape)
        .background(DarkBrown)
        .testTag(AvatarTag)
        .let { if (onClick != null) it.clickable(onClick = onClick) else it }

    // Display the avatar using AsyncImage
    if (isLoading) {
        LoadingImage(modifier = modifier)
    } else {
        SubcomposeAsyncImage(
            contentScale = ContentScale.Crop,
            model = avatar ?: R.drawable.user_icon,
            contentDescription = null,
            modifier = modifier,
            loading = { LoadingImage() }
        )
    }
}

@Composable
private fun LoadingImage(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.padding(4.dp),
        color = Color.Yellow
    )
}