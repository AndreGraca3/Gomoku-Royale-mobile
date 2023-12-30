package pt.isel.gomoku.ui.services

import io.mockk.coEvery
import io.mockk.mockk
import pt.isel.gomoku.domain.stats.Rank
import pt.isel.gomoku.http.model.MatchesStats
import pt.isel.gomoku.http.model.UserStatsOutputModel
import pt.isel.gomoku.http.model.WinStats
import pt.isel.gomoku.http.service.interfaces.StatsService

/** Stats services **/
val userStats1 = UserStatsOutputModel(
    rank = Rank(
        name = "Champion",
        iconUrl = "Champion_icon_url"
    ),
    winStats = WinStats(
        totalWins = 0,
        winsAsBlack = 0,
        winsAsWhite = 0,
        winRate = 0.0,
        draws = 0,
        loses = 0
    ),
    matchesStats = MatchesStats(
        totalMatches = 0,
        matchesAsBlack = 0,
        matchesAsWhite = 0
    )
)

val userStats2 = UserStatsOutputModel(
    rank = Rank(
        name = "Champion",
        iconUrl = "Champion_icon_url"
    ),
    winStats = WinStats(
        totalWins = 0,
        winsAsBlack = 0,
        winsAsWhite = 0,
        winRate = 0.0,
        draws = 0,
        loses = 0
    ),
    matchesStats = MatchesStats(
        totalMatches = 0,
        matchesAsBlack = 0,
        matchesAsWhite = 0
    )
)

val statsService = mockk<StatsService> {
    coEvery { getUserStats(1) } returns userStats1
    coEvery { getUserStats(2) } returns userStats2
}