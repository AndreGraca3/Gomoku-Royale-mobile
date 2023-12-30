package pt.isel.gomoku.ui.screens.match

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.http.model.MatchCreationOutputModel
import pt.isel.gomoku.http.model.MatchState

class MatchActivity : ComponentActivity() {

    companion object {
        const val MATCH_CREATION_EXTRA = "matchCreationExtra"
    }

    private val viewModel by viewModels<MatchScreenViewModel> {
        val app = (application as DependenciesContainer)
        MatchScreenViewModel.factory(app.userService, app.statsService, app.matchService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.match.collect {
                while (true) {
                    Log.v("Polling", "Polling...")
                    delay(3000)
                    viewModel.getMatch(matchCreationExtra.toMatchCreationOutputModel().id)
                }
            }
        }

        setContent {
            val match by viewModel.match.collectAsState(initial = idle())
            MatchScreen(
                users = listOf(
                    viewModel.currentUser,
                    viewModel.opponentUser
                ),
                match = match,
                onPlayRequested = { idMatch, move -> viewModel.play(idMatch, move) },
                onCancelRequested = {
                    viewModel.deleteSetupMatch()
                    finish()
                }
            )
        }
    }

    /**
     * Helper method to get the match extra from the intent.
     */
    private val matchCreationExtra: MatchCreationExtra by lazy {
        val extra = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(
                MATCH_CREATION_EXTRA,
                MatchCreationExtra::class.java
            )
        else
            intent.getParcelableExtra(MATCH_CREATION_EXTRA)

        checkNotNull(extra) { "No user details extra found in intent" }
    }
}

@Parcelize
data class MatchCreationExtra(
    val id: String,
    val state: MatchState
) : Parcelable {
    constructor(matchCreationOutputModel: MatchCreationOutputModel) : this(
        id = matchCreationOutputModel.id,
        state = matchCreationOutputModel.state
    )
}

fun MatchCreationExtra.toMatchCreationOutputModel() = MatchCreationOutputModel(
    id = id,
    state = state,
)