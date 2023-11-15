package pt.isel.gomoku.ui.screens.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pt.isel.gomoku.ui.screens.match.MatchActivity
import pt.isel.gomoku.utils.MusicService

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            // do some loading here while splash screen is shown
            false
        }

        // Start background music
        val svc = Intent(this, MusicService::class.java)
        // startService(svc) // comment this to avoid mental breakdowns while developing

        setContent {
            MenuScreen(
                onMatchRequested = { MatchActivity.navigateTo(this@MenuActivity) }
            )
        }
    }
}