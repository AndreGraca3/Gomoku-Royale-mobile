package pt.isel.gomoku.ui.screens.preferences

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.ui.screens.match.MatchActivity
import pt.isel.gomoku.ui.screens.match.MatchCreationExtra
import pt.isel.gomoku.utils.NavigateAux
import pt.isel.gomoku.utils.overrideTransition
import pt.isel.gomoku.utils.playSound

class PreferencesActivity : ComponentActivity() {

    companion object {
        const val MATCH_PRIVACY_EXTRA = "isPrivate"
    }

    private val vm by viewModels<PreferencesScreenViewModel> {
        val app = (application as DependenciesContainer)
        PreferencesScreenViewModel.factory(app.matchService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overrideTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top)

        lifecycleScope.launch {
            vm.matchState.collect {
                if (it is Loaded && it.value.isSuccess) {
                    NavigateAux.navigateTo<MatchActivity>(
                        this@PreferencesActivity,
                        MatchActivity.MATCH_CREATION_EXTRA,
                        MatchCreationExtra(it.value.getOrNull()!!)
                    )
                }
            }
        }

        setContent {
            val matchState by vm.matchState.collectAsState(initial = idle())
            PreferencesScreen(
                matchState = matchState,
                sizes = vm.sizes,
                variants = vm.variants,
                sizeSelected = vm.selectedSize,
                variantSelected = vm.selectedVariant,
                onSizeSelectRequested = {
                    if (it == vm.selectedSize) return@PreferencesScreen
                    this.playSound(R.raw.ui_click_2)
                    vm.selectedSize = it
                },
                onVariantSelectRequested = {
                    if (it == vm.selectedVariant) return@PreferencesScreen
                    this.playSound(R.raw.ui_click_2)
                    vm.selectedVariant = it
                },
                onCreateMatchRequested = { vm.createMatch(matchPrivacyExtra.isPrivate) },
            )
        }
    }

    /**
     * Helper method to get the match privacy extra from the intent.
     */
    private val matchPrivacyExtra: MatchPrivacyExtra by lazy {
        val extra = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(MATCH_PRIVACY_EXTRA, MatchPrivacyExtra::class.java)
        else
            intent.getParcelableExtra(MATCH_PRIVACY_EXTRA)

        checkNotNull(extra) { "No user details extra found in intent" }
    }
}

@Parcelize
data class MatchPrivacyExtra(val isPrivate: Boolean) : Parcelable
