package pt.isel.gomoku.ui.screens.leaderboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.gomoku.GomokuDependencyProvider
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.stats.UserRank

class LeaderBoardActivity : ComponentActivity() {

    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependencyProvider }

    /**
     * The view model for the main screen of the Jokes app.
     */
    private val viewModel by viewModels<LeaderBoardScreenViewModel> {
        LeaderBoardScreenViewModel.factory(dependencies.leaderBoardService)
    }

    companion object {
        fun navigate(origin: Activity) {
            with(origin) {
                val intent = Intent(this, LeaderBoardActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadTopPlayers()
        setContent {
            LeaderBoardScreen(viewModel.topPlayers)
        }
    }
}