package pt.isel.gomoku.utils

fun getRankIconByName(name: String): Int {
    return when (name) {
        "Silver" -> pt.isel.gomoku.R.drawable.silver
        "Grand Champion" -> pt.isel.gomoku.R.drawable.grand_champion
        else -> throw IllegalArgumentException("Invalid rank name")
    }
}