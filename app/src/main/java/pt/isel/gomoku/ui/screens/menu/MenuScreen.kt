package pt.isel.gomoku.ui.screens.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.screens.menu.topbar.MenuTopBar
import pt.isel.gomoku.ui.theme.GomokuTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(onMatchRequested: () -> Unit = {}, onLeaderBoardRequested: () -> Unit = {}) {
    GomokuTheme {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MenuTopBar()
            },
        ) {
            MenuBody(
                onMatchRequested = onMatchRequested,
                onLeaderBoardRequested = onLeaderBoardRequested
            )
        }
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen()
}