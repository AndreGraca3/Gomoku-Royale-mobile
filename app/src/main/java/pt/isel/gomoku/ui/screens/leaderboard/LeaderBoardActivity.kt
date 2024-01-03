package pt.isel.gomoku.ui.screens.leaderboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.ui.screens.user.UserDetailsActivity
import pt.isel.gomoku.ui.screens.user.UserItemExtra
import pt.isel.gomoku.utils.NavigateAux
import pt.isel.gomoku.utils.overrideTransition
import pt.isel.gomoku.utils.playSound

class LeaderBoardActivity : ComponentActivity() {

    private val viewModel by viewModels<LeaderBoardScreenViewModel> {
        val app = (application as DependenciesContainer)
        LeaderBoardScreenViewModel.factory(app.leaderBoardService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overrideTransition(R.anim.pop_up_in, R.anim.pop_up_out)

        viewModel.loadTopPlayers()

        setContent {
            val leaderBoard by viewModel.topPlayers.collectAsState(initial = idle())
            LeaderBoardScreen(
                leaderBoard = leaderBoard,
                onBackRequested = { finish() },
                onPlayerRequested = { id ->
                    this.playSound(R.raw.ui_click_4)
                    NavigateAux.navigateTo<UserDetailsActivity>(
                        this,
                        UserDetailsActivity.USER_ITEM_EXTRA,
                        UserItemExtra(id)
                    )
                }
            )
        }
    }
}