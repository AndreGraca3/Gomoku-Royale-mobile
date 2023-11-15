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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.screens.menu.logo.BackgroundCloud
import pt.isel.gomoku.ui.screens.menu.topbar.MenuTopBar
import pt.isel.gomoku.ui.theme.GomokuTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(onMatchRequested: () -> Unit = {}) {
    GomokuTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.background_theme),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            repeat(2) {
                val dir = if (it % 2 == 0) 1 else -1
                BackgroundCloud(
                    300F * dir,
                    -LocalConfiguration.current.screenHeightDp.toFloat() / (it + 2),
                    dir
                )
            }

            Scaffold(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    MenuTopBar()
                },
            ) {
                MenuBody(onMatchRequested)
            }
        }
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    GomokuTheme {
        MenuScreen()
    }
}