package pt.isel.gomoku.ui.screens.match

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.http.model.MatchCreationOutputModel
import pt.isel.gomoku.http.model.MatchState
import pt.isel.gomoku.utils.playSound

class MatchActivity : ComponentActivity() {

    companion object {
        const val MATCH_CREATION_EXTRA = "matchCreationExtra"
        const val MATCH_ENDED_DELAY = 5000L
    }

    private val vm by viewModels<MatchScreenViewModel> {
        val app = (application as DependenciesContainer)
        MatchScreenViewModel.factory(app.userService, app.statsService, app.matchService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.getMatch(matchCreationExtra.toMatchCreationOutputModel().id)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.events.collect { event ->
                    when (event) {
                        MatchEvent.PLAYED,
                        MatchEvent.OPPONENT_PLAYED -> {
                            playSound(listOf(R.raw.place_piece_1, R.raw.place_piece_2).random())
                        }

                        MatchEvent.DELETE_SETUP_MATCH -> {
                            finish()
                        }

                        MatchEvent.MATCH_ENDED -> {
                            playSound(R.raw.place_piece_winner)
                            delay(MATCH_ENDED_DELAY)
                            finish()
                        }

                        else -> Unit
                    }
                }
            }
        }

        setContent {
            val match by vm.match.collectAsState(initial = idle())
            val pendingPlay by vm.pendingPlay.collectAsState(initial = idle())
            val events by vm.events.collectAsState(initial = MatchEvent.SETUP)

            if (events == MatchEvent.MATCH_ENDED)
                SweetSuccess(
                    "${match.getOrNull()?.board?.turn} won!",
                    contentAlignment = Alignment.TopCenter
                )

            MatchScreen(
                users = listOf(vm.localUser, vm.opponentUser),
                matchIOState = match,
                pendingPlay = pendingPlay,
                onPlayRequested = { idMatch, move -> vm.play(idMatch, move) },
                onBackRequested = { vm.deleteSetupMatch() },
            )
        }

        onBackPressedDispatcher.addCallback(this, true) {
            vm.deleteSetupMatch()
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