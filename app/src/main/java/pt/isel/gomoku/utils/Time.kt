package pt.isel.gomoku.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timeSince(timestamp: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    val date = format.parse(timestamp)
    val now = Date()

    val diff = now.time - date.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 0 -> "$days days"
        hours > 0 -> "$hours hours"
        minutes > 0 -> "$minutes minutes"
        else -> "$seconds seconds"
    }
}
