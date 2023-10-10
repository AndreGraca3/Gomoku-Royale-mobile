package pt.isel.gomoku.ui.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pt.isel.gomoku.domain.Match
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.ui.screens.match.MatchActivity
import pt.isel.gomoku.ui.screens.match.MatchScreen
import pt.isel.gomoku.ui.screens.match.MatchScreenViewModel
import pt.isel.gomoku.ui.theme.GomokuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                // do some loading here while splash screen is shown
                false
            }
        }
        setContent {
            /*MainScreen(
                onBackRequested = { finish() },
                onMatchRequested = { MatchActivity.navigateTo(this) }
            )*/
            MatchScreen()
        }
    }
}