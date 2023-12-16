package pt.isel.gomoku.ui.screens.match

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.ui.screens.login.LoginScreenViewModel
import pt.isel.gomoku.utils.viewModelInit

class MatchActivity : ComponentActivity() {

    companion object {
        fun navigate(origin: Activity) {
            with(origin) {
                val intent = Intent(this, MatchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val vm by viewModels<MatchScreenViewModel> {
        MatchScreenViewModel.factory((application as DependenciesContainer).matchService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatchScreen(onBackRequested = { finish() })
        }
    }
}