package pt.isel.gomoku.ui.screens.profile

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import kotlinx.parcelize.Parcelize
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.http.model.UserDetails
import java.io.ByteArrayOutputStream


class ProfileActivity : ComponentActivity() {

    companion object {
        const val USER_DETAILS_EXTRA = "UserDetails"
    }

    private val viewModel by viewModels<ProfileScreenViewModel> {
        val app = (application as DependenciesContainer)
        ProfileScreenViewModel.factory(app.userService, app.tokenRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.name = userDetailsExtra.toUserDetails().name
        viewModel.avatar = userDetailsExtra.toUserDetails().avatarUrl

        setContent {

            val launcher = rememberLauncherForActivityResult(
                contract =
                ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                if (uri != null) {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

                    val outputStream = ByteArrayOutputStream()

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

                    viewModel.avatar = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
                    viewModel.updateUserRequest()
                }
            }
            ProfileScreen(
                userDetails = userDetailsExtra.toUserDetails(),
                name = viewModel.name,
                avatar = viewModel.avatar,
                isEditing = viewModel.isEditing,
                onLogoutRequested = {
                    viewModel.logout()
                    finish()
                },
                onNameChange = { viewModel.name = it },
                onAvatarChange = {
                    launcher.launch("image/*")
                },
                onEditRequest = { viewModel.changeToEditMode() },
                onFinishEdit = { viewModel.updateUserRequest() }
            )
        }
    }

    /**
     * Helper method to get the user details extra from the intent.
     */
    private val userDetailsExtra: UserDetailsExtra by lazy {
        val extra = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
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