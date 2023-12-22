package pt.isel.gomoku.ui.screens.match

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.gomoku.DependenciesContainer

class MatchActivity : ComponentActivity() {

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