package pt.isel.gomoku.ui.screens.leaderboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.idle

class LeaderBoardActivity : ComponentActivity() {

    private val viewModel by viewModels<LeaderBoardScreenViewModel> {
        val app = (application as DependenciesContainer)
        LeaderBoardScreenViewModel.factory(app.leaderBoardService)
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
        // if build is 34 or higher, use the following line instead:
        if (android.os.Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(
                OVERRIDE_TRANSITION_OPEN,
                R.anim.pop_up_in,
                R.anim.pop_up_out
            )
        } else {
            overridePendingTransition(R.anim.pop_up_in, R.anim.pop_up_out)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadTopPlayers()
            }
        }

        setContent {
            val leaderBoard by viewModel.topPlayers.collectAsState(initial = idle())
            LeaderBoardScreen(leaderBoard)
        }
    }
}