package pt.isel.gomoku.utils

import pt.isel.gomoku.R

fun getRankIconByName(name: String): Int {
    return when (name) {
        "Grand Champion" -> R.drawable.grand_champion
        "Champion" -> R.drawable.champion
        "Diamond" -> R.drawable.diamond
        "Platinum" -> R.drawable.platinum
        "Gold" -> R.drawable.gold
        "Silver" -> R.drawable.silver
        "Bronze" -> R.drawable.bronze
        else -> throw IllegalArgumentException("Invalid rank name")
    }
}