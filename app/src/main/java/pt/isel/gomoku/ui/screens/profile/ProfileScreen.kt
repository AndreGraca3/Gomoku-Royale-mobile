package pt.isel.gomoku.ui.screens.profile

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProfileScreen(onLogoutRequested: () -> Unit) {
    Button(onClick = onLogoutRequested) {
        Text(text = "Logout")
    }
}