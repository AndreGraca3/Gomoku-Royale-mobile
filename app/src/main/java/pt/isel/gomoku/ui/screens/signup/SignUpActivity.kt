package pt.isel.gomoku.ui.screens.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import pt.isel.gomoku.ui.theme.GomokuTheme

class SignUpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                Text(text = "Hello World!")
            }
            // SignUpScreen()
        }
    }
}