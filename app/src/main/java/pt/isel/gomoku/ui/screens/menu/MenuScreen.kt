package pt.isel.gomoku.ui.screens.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.http.model.user.UserInfo
import pt.isel.gomoku.ui.screens.menu.topbar.MenuTopBar
import pt.isel.gomoku.ui.theme.GomokuTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    userInfoState: IOState<UserInfo?>,
    isInitialized: Boolean = true,
    onAvatarClick: () -> Unit = {},
    onMatchRequested: () -> Unit = {},
    onLeaderBoardRequested: () -> Unit = {},
    onAboutRequested: () -> Unit = {}
) {
    val userInfo = userInfoState.getOrNull()
    GomokuTheme {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MenuTopBar(userInfo?.avatarUrl, onAvatarClick)
            },
        ) { innerPadding ->
            MenuBody(
                innerPadding = innerPadding,
                isInitialized = isInitialized,
                isLoggedIn = userInfo != null,
                onMatchRequested = onMatchRequested,
                onLeaderBoardRequested = onLeaderBoardRequested,
                onAboutRequested = onAboutRequested
            )
        }
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(idle())
}