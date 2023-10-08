package pt.isel.gomoku.ui.screens.match

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.viewModelInit

class MatchActivity : ComponentActivity() {

    private val viewModel by viewModels<MatchScreenViewModel> {
        viewModelInit {
            TODO()
            //val app = (application as DependenciesContainer)
            //MatchScreenViewModel(app.match)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                TODO()
                // MatchScreen()
            }
        }
    }
}