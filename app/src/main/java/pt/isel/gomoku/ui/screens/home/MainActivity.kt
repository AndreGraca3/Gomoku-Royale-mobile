package pt.isel.gomoku.ui.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pt.isel.gomoku.ui.screens.match.MatchActivity

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
            MainScreen(
                onBackRequested = { finish() },
                onMatchRequested = { MatchActivity.navigateTo(this) }
            )
        }
    }
}