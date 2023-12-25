package pt.isel.gomoku.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.ui.components.common.IOResourceLoader
import pt.isel.gomoku.ui.theme.GomokuTheme

@Composable
fun ProfileScreen(
    userDetailsState: IOState<UserDetails>,
    onLogoutRequested: () -> Unit
) {
    GomokuTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IOResourceLoader(resource = userDetailsState) {
                AvatarIcon(it.role)

                Text(text = it.name)

                Text(text = it.email)

                Text(text = "Playing since: ${it.createdAt}")

                Button(onClick = onLogoutRequested) {
                    Text(text = "Logout")
                }
            }
        }
    }
}

@Composable
fun AvatarIcon(role: String = "user") {
    Box {
        Image(
            painter = painterResource(R.drawable.diogo_avatar),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(90.dp)
                .border(2.dp, Color.White, CircleShape)
                .clip(CircleShape)
        )
        if (role == "admin") {
            Text(
                text = "👑",
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
        }
        Text(
            text = "\uD83D\uDCF7",
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
    }
}