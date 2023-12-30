package pt.isel.gomoku.ui.screens.profile

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TimeDisplay(text: String, time: String) {
    val date = time.split("T")[0]
    /* TODO = formatter*/

    Row {
        Text(text = text)
        Text(text = date)
    }
}