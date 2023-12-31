package pt.isel.gomoku.ui.screens.stats

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.utils.overrideTransition

class StatsActivity : ComponentActivity() {

    companion object {
        const val USER_ID_EXTRA = "UserId"
    }

    private val viewModel by viewModels<StatsScreenViewModel> {
        val app = (application as DependenciesContainer)
        StatsScreenViewModel.factory(app.statsService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overrideTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top)

        viewModel.fetchUserStats(userIdExtra.toId())

        setContent {
            val userStats by viewModel.userStats.collectAsState(initial = idle())
            StatsScreen(userStats = userStats)
        }
    }

    /**
     * Helper method to get the user details extra from the intent.
     */
    private val userIdExtra: UserIdExtra by lazy {
        val extra = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(USER_ID_EXTRA, UserIdExtra::class.java)
        else
            intent.getParcelableExtra(USER_ID_EXTRA)

        checkNotNull(extra) { "No user id extra found in intent" }
    }
}

@Parcelize
data class UserIdExtra(
    val id: Int,
) : Parcelable

fun UserIdExtra.toId(): Int = this.id
