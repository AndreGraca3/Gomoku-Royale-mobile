package pt.isel.gomoku.ui.screens.match

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.lifecycleScope
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetInfo
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetWarning
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.http.model.MatchCreationOutputModel
import pt.isel.gomoku.http.model.MatchState
import pt.isel.gomoku.utils.playSound
import java.util.concurrent.TimeUnit

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

        vm.getMatchAndStartPolling(matchCreationExtra.toMatchCreationOutputModel().id)

        lifecycleScope.launch {
            vm.events.collect { event ->
                when (event) {
                    is MatchEvent.OpponentJoined -> {
                        playSound(R.raw.opponent_found)
                    }

                    is MatchEvent.Played -> {
                        playSound(listOf(R.raw.place_piece_1, R.raw.place_piece_2).random())
                    }

                    is MatchEvent.DeleteSetupMatch -> {
                        finish()
                    }

                    is MatchEvent.MatchEnded -> {
                        playSound(R.raw.place_piece_winner)
                        delay(MATCH_ENDED_DELAY)
                        finish()
                    }

                    else -> Unit
                }
            }
        }

        setContent {
            val match by vm.match.collectAsState(initial = idle())
            val pendingPlayDot by vm.pendingPlay.collectAsState(initial = null)
            val events by vm.events.collectAsState(initial = MatchEvent.Setup)

            when (val event = events) {
                is MatchEvent.MatchEnded -> {
                    if (event.winner) {
                        KonfettiView(
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(1F),
                            parties = listOf(
                                Party(
                                    speed = 0f,
                                    maxSpeed = 15f,
                                    damping = 0.9f,
                                    angle = Angle.BOTTOM,
                                    spread = Spread.ROUND,
                                    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                                    emitter = Emitter(
                                        duration = 3,
                                        TimeUnit.SECONDS
                                    ).perSecond(100),
                                    position = Position.Relative(0.0, 0.0)
                                        .between(Position.Relative(1.0, 0.0))
                                )
                            )
                        )
                    }
                    SweetInfo(
                        "You ${if (event.winner) "won" else "lost"}!",
                        contentAlignment = Alignment.TopCenter
                    )
                }

                is MatchEvent.Error -> {
                    SweetWarning(
                        event.errorMessage ?: "Oops! Couldn't register play",
                        contentAlignment = Alignment.TopCenter
                    )
                }

                else -> Unit
            }

            MatchScreen(
                screenState = vm.screenState,
                users = listOf(vm.localUser, vm.opponentUser),
                matchIOState = match,
                pendingPlayDot = pendingPlayDot,
                onCancelRequested = { vm.deleteSetupMatch() },
                onBackRequested = { finish() },
                onForfeitRequested = { vm.forfeit(matchCreationExtra.toMatchCreationOutputModel().id) },
                onPlayRequested = { idMatch, move -> vm.play(idMatch, move) }
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