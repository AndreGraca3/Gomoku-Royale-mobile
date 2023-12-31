package pt.isel.gomoku.ui.screens.menu.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.common.AsyncAvatar
import pt.isel.gomoku.ui.components.text.TextShimmer
import pt.isel.gomoku.ui.theme.DarkBrown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar(
    isLoading: Boolean,
    userAvatar: String?,
    onAvatarClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.shadow(50.dp, ambientColor = Color.Black, spotColor = Color.Black),
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = DarkBrown),
        title = { TextShimmer(getString(LocalContext.current, R.string.app_name)) },
        actions = {
            AsyncAvatar(userAvatar, isLoading = isLoading, onClick = onAvatarClick)
        }
    )
}