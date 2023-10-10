package pt.isel.gomoku.ui.screens.match

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.screens.home.MainActivity
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.playSound
import pt.isel.gomoku.utils.viewModelInit

class MatchActivity : ComponentActivity() {

    companion object {
        fun navigateTo(origin: Activity) {
            val intent = Intent(origin, MatchActivity::class.java)
            origin.startActivity(intent)
        }
    }

    private val viewModel by viewModels<MatchScreenViewModel> {
        viewModelInit {
            // TODO
            //val app = (application as DependenciesContainer)
            //MatchScreenViewModel(app.match)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatchScreen(onBackRequested = {
                playSound(this, R.raw.click)
                finish()
            })
        }
    }
}