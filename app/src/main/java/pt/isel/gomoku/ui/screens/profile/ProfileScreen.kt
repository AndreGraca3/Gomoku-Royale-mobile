package pt.isel.gomoku.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.exceptionOrNull
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.http.service.result.APIException
import pt.isel.gomoku.ui.components.buttons.ScaledButton
import pt.isel.gomoku.ui.components.common.AsyncAvatar
import pt.isel.gomoku.ui.components.common.LoadingDots
import pt.isel.gomoku.ui.components.layouts.RoundedLayout
import pt.isel.gomoku.ui.components.text.GomokuTextField
import pt.isel.gomoku.ui.theme.GomokuTheme

const val ProfileScreenTag = "ProfileScreenTag"

@Composable
fun ProfileScreen(
    userDetailsState: IOState<UserDetails>,
    name: String,
    email: String,
    createdAt: String,
    onLogoutRequested: () -> Unit,
    onNameChange: (String) -> Unit,
    onAvatarChange: () -> Unit,
    onUpdateRequested: () -> Unit
) {
    val userDetails = userDetailsState.getOrNull()

    GomokuTheme(background = R.drawable.grid_background) {
        RoundedLayout {
            AsyncAvatar(
                avatar = userDetails?.avatarUrl,
                isLoading = userDetailsState is Loading,
                size = 200.dp,
                onClick = onAvatarChange,
            )

            when (val e = userDetailsState.exceptionOrNull()) {
                is APIException -> {
                    SweetError(
                        message = e.problem.detail,
                        contentAlignment = Alignment.TopCenter,
                    )
                }
            }

            Column(
                modifier = Modifier.height(30.dp)
            ) {
                if (userDetailsState is Loading) LoadingDots()
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(90.dp)
                    .padding(8.dp)
            ) {

                GomokuTextField(
                    value = name,
                    enabled = userDetailsState !is Loading,
                    onValueChange = onNameChange,
                    label = { Text("Name") },
                )

                ScaledButton(
                    text = "✔️",
                    color = Color.Green,
                    enabled = userDetailsState !is Loading,
                    shape = RoundedCornerShape(8.dp),
                    onClick = onUpdateRequested,
                    modifier = Modifier.fillMaxHeight(0.6F)
                )
            }

            GomokuTextField(
                value = email,
                enabled = false,
                label = { Text("Email") },
            )
            TimeDisplay(createdAt)
            ScaledButton(
                text = "Logout \uD83D\uDC4B",
                color = Color.Red,
                onClick = onLogoutRequested
            )
        }
    }
}
