package pt.isel.gomoku.ui.screens.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pt.isel.gomoku.ui.screens.menu.MenuActivity
import pt.isel.gomoku.ui.theme.GomokuTheme

class SignUpActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: Activity) {
            val intent = Intent(origin, SignUpActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        SignUpScreen(
                            onSignInRequest = {
                                MenuActivity.navigateTo(this@SignUpActivity)
                            }
                        )
                    }
                }
            }
        }
    }
}