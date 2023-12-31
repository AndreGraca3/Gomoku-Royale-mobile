package pt.isel.gomoku.ui.components.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.theme.DarkBrown
import pt.isel.gomoku.ui.theme.Yellow

@OptIn(ExperimentalFoundationApi::class)
const val AvatarTag = "MainScreenAvatarTag"
val userAvatar = SemanticsPropertyKey<String?>("UserAvatar")
var SemanticsPropertyReceiver.userAvatar by userAvatar

@Composable
fun AsyncAvatar(
    avatar: String? = null,
    size: Dp = 50.dp,
    isLoading: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null
) {
    require(onLongPress == null || onClick != null) {
        "onLongPress requires onClick to be set"
    }

    // Combine modifiers based on conditions
    val modifier = Modifier
        .size(size)
        .border(2.dp, Color.White, CircleShape)
        .clip(CircleShape)
        .background(DarkBrown)
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { onClick?.invoke() },
                onLongPress = { onLongPress?.invoke() }
            )
        }
        .testTag(AvatarTag)

    // TODO: Add a loading indicator

    // Display the avatar using AsyncImage
    AsyncImage(
        contentScale = ContentScale.Crop,
        model = avatar ?: R.drawable.user_icon,
        contentDescription = null,
        modifier = modifier
    )
}