package pt.isel.gomoku.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pt.isel.gomoku.ui.screens.login.LoginScreen
import pt.isel.gomoku.ui.screens.menu.MenuActivity
import pt.isel.gomoku.ui.screens.signup.SignUpActivity
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
            GomokuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoginScreen(
                            onLoginRequest = {
                                MenuActivity.navigateTo(this@MainActivity)
                            },
                            onSignInRequest = {
                                SignUpActivity.navigateTo(this@MainActivity)
                            }
                        )
                    }
                }
            }
        }
    }
}