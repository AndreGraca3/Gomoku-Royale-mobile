package pt.isel.gomoku.ui.screens.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.http.model.UserDetails

private const val USER_DETAILS_EXTRA = "UserDetails"

class ProfileActivity : ComponentActivity() {

    companion object {
        fun createIntent(ctx: Context, userDetails: UserDetails? = null): Intent {
            val intent = Intent(ctx, ProfileActivity::class.java)
            userDetails?.let { intent.putExtra(USER_DETAILS_EXTRA, UserDetailsExtra(it)) }
            return intent
        }
    }

    private val viewModel by viewModels<ProfileScreenViewModel> {
        val app = (application as DependenciesContainer)
        ProfileScreenViewModel.factory(app.userService, app.tokenRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchUserDetails()
            }
        }

        setContent {
            val userDetails by viewModel.userDetails.collectAsState(initial = idle())
            ProfileScreen(
                userDetailsState = userDetails,
            ) {
                viewModel.logout()
                finish()
            }
        }
    }
}

@Parcelize
data class UserDetailsExtra(
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String,
    val role: String,
    val createdAt: String,
) : Parcelable {
    constructor(userDetails: UserDetails) : this(
        id = userDetails.id,
        name = userDetails.name,
        email = userDetails.email,
        avatarUrl = userDetails.avatarUrl,
        role = userDetails.role,
        createdAt = userDetails.createdAt,
    )
}

fun UserDetailsExtra.toUserDetails() = UserDetails(
    id = id,
    name = name,
    email = email,
    avatarUrl = avatarUrl,
    role = role,
    createdAt = createdAt
)