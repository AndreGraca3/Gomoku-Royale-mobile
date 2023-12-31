package pt.isel.gomoku.ui.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import pt.isel.gomoku.utils.timeSince

@Composable
fun TimeDisplay(time: String) {
    val since = timeSince(time)
    val sinceParts = since.split(" ")
    var actualTime by remember { mutableIntStateOf(0) }
    val joinedValue = sinceParts[0].toInt()

    LaunchedEffect(Unit) {
        while (true) {
            val delay = if (joinedValue - actualTime < 50) 10L else 5L
            delay(delay)
            actualTime += 1
            if (actualTime == joinedValue) break
        }
    }

    Text(text = "Joined: $actualTime ${sinceParts[1]} ago")
}