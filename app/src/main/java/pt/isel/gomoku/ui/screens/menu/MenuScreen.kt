package pt.isel.gomoku.ui.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.screens.menu.topbar.MenuTopBar
import pt.isel.gomoku.ui.theme.GomokuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(onMatchRequested: () -> Unit = {}) {
    GomokuTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
            )
            Scaffold(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                topBar = {
                    MenuTopBar()
                },
            ) { innerPadding ->
                MenuBody(innerPadding, onMatchRequested)
            }
        }
    }
}