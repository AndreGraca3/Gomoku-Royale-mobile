package pt.isel.gomoku.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.ui.theme.Brown
import pt.isel.gomoku.ui.theme.DarkBrown
import pt.isel.gomoku.ui.theme.GomokuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userDetails: UserDetails,
    name: String,
    avatar: String?,
    isEditing: Boolean,
    onLogoutRequested: () -> Unit,
    onNameChange: (String) -> Unit,
    onAvatarChange: (String?) -> Unit,
    onEditRequest: () -> Unit,
    onFinishEdit: () -> Unit
) {
    GomokuTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize(0.8F)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brown)
                    .border(5.dp, color = DarkBrown, shape = RoundedCornerShape(20.dp))
            ) {
                Text(
                    text = "User profile",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )

                AvatarIcon(
                    avatar = avatar,
                    userDetails.role,
                    onAvatarChange = onAvatarChange
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isEditing) {
                        OutlinedTextField(
                            value = name,
                            placeholder = { Text(text = userDetails.name) },
                            onValueChange = onNameChange,
                            modifier = Modifier
                                .size(230.dp, 60.dp)
                        )

                        Text(
                            text = "✔️",
                            modifier = Modifier
                                .clickable { onFinishEdit() }
                        )
                    } else {
                        Text(
                            text = name // is this correct?
                        )

                        Text(
                            text = "✏️",
                            modifier = Modifier
                                .clickable {
                                    onEditRequest()
                                }
                        )
                    }
                }

                Text(
                    text = userDetails.email
                )

                TimeDisplay(
                    text = "Playing since:",
                    time = userDetails.createdAt
                )

                Button(
                    onClick = onLogoutRequested
                ) {
                    Text(text = "Logout")
                }
            }
        }
    }
}
