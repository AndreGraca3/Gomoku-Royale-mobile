package pt.isel.gomoku.ui.services

import io.mockk.coEvery
import io.mockk.mockk
import pt.isel.gomoku.domain.stats.Rank
import pt.isel.gomoku.http.model.LeaderBoard
import pt.isel.gomoku.http.model.UserItem
import pt.isel.gomoku.http.service.interfaces.LeaderBoardService

/** leaderBoard service**/
val leaderBoard = LeaderBoard(
    listOf(
        UserItem(
            1,
            "Diogo",
            "admin",
            Rank(
                "Champion",
                "Champion_url"
            )
        )
    )
)

val leaderBoardService = mockk<LeaderBoardService> {
    coEvery { getTopPlayers(any()) } returns leaderBoard
}