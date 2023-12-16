package pt.isel.gomoku.http.model.stats

import pt.isel.gomoku.domain.stats.Rank

data class UserItem(
    val id: Int,
    val name: String,
    val role: String,
    val rank: Rank,
)