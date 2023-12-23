package pt.isel.gomoku.ui.screens.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.http.model.user.UserDetails

private const val USER_DETAILS_EXTRA = "UserDetails"

class ProfileActivity : ComponentActivity() {

    companion object {
        fun createIntent(ctx: Context, userDetails: UserDetails? = null): Intent {
            val intent = Intent(ctx, ProfileActivity::class.java)
            userDetails?.let { intent.putExtra(USER_DETAILS_EXTRA, UserDetailsExtra(it)) }
            return intent
        }
    }

    private val vm by viewModels<ProfileScreenViewModel> {
        val app = (application as DependenciesContainer)
        ProfileScreenViewModel.factory(app.userService, app.tokenRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProfileScreen(
                onLogoutRequested = {
                    vm.logout()
                    finish()
                },
            )
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