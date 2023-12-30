package pt.isel.gomoku.http.model

import pt.isel.gomoku.domain.stats.Rank

data class LeaderBoard(val ranks: List<UserItem>)

class UserStatsOutputModel(val rank: Rank, val winStats: WinStats, val matchesStats: MatchesStats)

data class WinStats(
    val totalWins: Int,
    val winsAsBlack: Int,
    val winsAsWhite: Int,
    val winRate: Double,
    val draws: Int,
    val loses: Int,
)

data class MatchesStats(
    val totalMatches: Int,
    val matchesAsBlack: Int,
    val matchesAsWhite: Int
)