package pt.isel.gomoku.ui.screens.menu.topbar

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.common.AsyncAvatar
import pt.isel.gomoku.ui.components.text.TextShimmer
import pt.isel.gomoku.utils.clickableWithoutRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar(
    userAvatar: String?,
    onAvatarClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.shadow(50.dp, ambientColor = Color.Black, spotColor = Color.Black),
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF4E2608)),
        title = { TextShimmer(getString(LocalContext.current, R.string.app_name)) },
        actions = {
            //Column(
            //    modifier = Modifier
            //        .padding(4.dp)
            //        .clickableWithoutRipple { onAvatarClick() }
            //) {
                AsyncAvatar(userAvatar, onClick = onAvatarClick)
            //}
        }
    )
}