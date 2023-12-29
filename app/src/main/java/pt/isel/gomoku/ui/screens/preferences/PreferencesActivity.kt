package pt.isel.gomoku.ui.screens.preferences

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.ui.screens.match.MatchActivity
import pt.isel.gomoku.ui.screens.match.MatchCreationExtra
import pt.isel.gomoku.utils.NavigateAux

class PreferencesActivity : ComponentActivity() {

    companion object {
        const val MATCH_PRIVACY_EXTRA = "isPrivate"
    }

    private val viewModel by viewModels<PreferencesScreenViewModel> {
        val app = (application as DependenciesContainer)
        PreferencesScreenViewModel.factory(app.matchService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.matchState.collect {
                if (it is Loaded && it.value.isSuccess) {
                    Log.v(
                        "Preferences screen",
                        "finished loading match with result: ${it.value.getOrNull()!!}"
                    )
                    setResult(RESULT_OK)
                    NavigateAux.navigateTo<MatchActivity>(
                        this@PreferencesActivity,
                        MatchActivity.MATCH_CREATION_EXTRA,
                        MatchCreationExtra(it.value.getOrNull()!!)
                    )
                }
            }
        }

        setContent {
            PreferencesScreen(
                sizes = viewModel.sizes,
                variants = viewModel.variants,
                sizeSelected = viewModel.size,
                variantSelected = viewModel.variant,
                onSizeSelectRequested = { viewModel.size = it },
                onVariantSelectRequested = { viewModel.variant = it },
                onCreateMatchRequested = { viewModel.createMatch(matchPrivacyExtra.toIsPrivate()) },
            )
        }
    }

    /**
     * Helper method to get the user details extra from the intent.
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
data class MatchPrivacyExtra(
    val isPrivate: Boolean,
) : Parcelable

fun MatchPrivacyExtra.toIsPrivate() = this.isPrivate