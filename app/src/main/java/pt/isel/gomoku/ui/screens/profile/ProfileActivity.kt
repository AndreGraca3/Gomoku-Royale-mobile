package pt.isel.gomoku.ui.screens.profile

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.utils.overrideTransition
import pt.isel.gomoku.utils.playSound


class ProfileActivity : ComponentActivity() {

    companion object {
        const val USER_DETAILS_EXTRA = "UserDetails"
    }

    private val vm by viewModels<ProfileScreenViewModel> {
        val app = (application as DependenciesContainer)
        ProfileScreenViewModel.factory(contentResolver, app.userService, app.tokenRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overrideTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top)

        vm.initializeUserDetails(userDetailsExtra.toUserDetails())
        vm.name = userDetailsExtra.toUserDetails().name

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.userDetails.collect {
                    if (it is Loaded) this@ProfileActivity.playSound(R.raw.ui_click_1)
                }
            }
        }

        setContent {
            val userDetails by vm.userDetails.collectAsState(initial = idle())
            val launcher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { contentPath ->
                        if(contentPath != null) {
                            vm.avatarPath = contentPath
                            vm.updateUser()
                            setResult(RESULT_OK)
                        }
                    }
                )

            ProfileScreen(
                userDetailsState = userDetails,
                name = vm.name,
                email = userDetailsExtra.email,
                createdAt = userDetailsExtra.createdAt,
                onLogoutRequested = {
                    vm.logout(onLogout = {
                        setResult(RESULT_OK)
                        finish()
                    })
                },
                onNameChange = { vm.name = it },
                onAvatarChange = {
                    this.playSound(R.raw.ui_click_2)
                    launcher.launch("image/*")
                },
                onUpdateRequested = {
                    vm.updateUser()
                    setResult(RESULT_OK)
                }
            )
        }
    }

    /**
     * Helper method to get the user details extra from the intent.
     */
    private val userDetailsExtra: UserDetailsExtra by lazy {
        val extra =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
                intent.getParcelableExtra(USER_DETAILS_EXTRA, UserDetailsExtra::class.java)
            else
                intent.getParcelableExtra(USER_DETAILS_EXTRA)

        checkNotNull(extra) { "No user details extra found in intent" }
    }
}

@Parcelize
data class UserDetailsExtra(
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String?,
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