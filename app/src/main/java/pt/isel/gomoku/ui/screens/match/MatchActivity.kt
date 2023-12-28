package pt.isel.gomoku.ui.screens.match

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.ui.screens.menu.MenuActivity
import pt.isel.gomoku.utils.NavigateAux

class MatchActivity : ComponentActivity() {

    private val viewModel by viewModels<MatchScreenViewModel> {
        MatchScreenViewModel.factory((application as DependenciesContainer).matchService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val match by viewModel.match.collectAsState(initial = idle())
            MatchScreen(
                match = match,
                onCancelRequested = {
                    viewModel.deleteSetupMatch(match.getOrNull()!!.id)
                    NavigateAux.navigateTo<MenuActivity>(this)
                }
            )
        }
    }
}