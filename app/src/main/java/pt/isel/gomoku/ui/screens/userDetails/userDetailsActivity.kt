package pt.isel.gomoku.ui.screens.userDetails

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.domain.idle

class UserDetailsActivity : ComponentActivity() {

    companion object {
        const val USER_ITEM_EXTRA = "UserItemExtra"
    }

    private val viewModel by viewModels<UserDetailsScreenViewModel> {
        val app = (application as DependenciesContainer)
        UserDetailsScreenViewModel.factory(app.userService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchUser(userItemExtra.id)

        setContent {
            val userInfo by viewModel.userDetails.collectAsState(initial = idle())
            UserDetailsScreen(userInfo = userInfo)
        }
    }

    /**
     * Helper method to get the user details extra from the intent.
     */
    private val userItemExtra: UserItemExtra by lazy {
        val extra = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(USER_ITEM_EXTRA, UserItemExtra::class.java)
        else
            intent.getParcelableExtra(USER_ITEM_EXTRA)

        checkNotNull(extra) { "No user details extra found in intent" }
    }
}

@Parcelize
data class UserItemExtra(
    val id: Int,
) : Parcelable