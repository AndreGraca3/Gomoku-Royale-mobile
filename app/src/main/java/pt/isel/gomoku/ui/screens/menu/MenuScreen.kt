package pt.isel.gomoku.ui.screens.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.ui.screens.menu.topbar.MenuTopBar
import pt.isel.gomoku.ui.theme.GomokuTheme

const val MenuScreenTag = "MenuScreenTag"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    userInfoState: IOState<UserDetails?>,
    onAvatarClick: () -> Unit = {},
    onMatchRequested: (Boolean) -> Unit = {},
    onLeaderBoardRequested: () -> Unit = {},
    onAboutRequested: () -> Unit = {},
    onStatsRequested: () -> Unit = {}
) {
    val userInfo = userInfoState.getOrNull()
    GomokuTheme {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
                .testTag(MenuScreenTag),
            topBar = {
                MenuTopBar(
                    userAvatar = userInfo?.avatarUrl,
                    isLoading = userInfoState is Loading,
                    onAvatarClick = onAvatarClick
                )
            },
        ) { innerPadding ->
            MenuBody(
                innerPadding = innerPadding,
                isInitialized = userInfoState !is Loading,
                isLoggedIn = userInfo != null,
                onMatchRequested = onMatchRequested,
                onLeaderBoardRequested = onLeaderBoardRequested,
                onAboutRequested = onAboutRequested,
                onStatsRequested = onStatsRequested
            )
        }
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(idle())
}