package pt.isel.gomoku.ui.services

import io.mockk.coEvery
import io.mockk.mockk
import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.domain.game.match.Player
import pt.isel.gomoku.http.model.MatchCreationOutputModel
import pt.isel.gomoku.http.model.MatchState
import pt.isel.gomoku.http.model.PlayOutputModel
import pt.isel.gomoku.http.service.interfaces.MatchService

/** Match services **/
val matchCreationOutput = MatchCreationOutputModel(
    "id",
    MatchState.SETUP
)

val match = Match(
    id = matchCreationOutput.id,
    isPrivate = true,
    variant = "FreeStyle",
    blackId = 1,
    whiteId = 2,
    state = MatchState.ONGOING,
    board = Board(
        size = 15,
        stones = listOf(),
        turn = Player.BLACK
    )
)

private val dot = Dot(
    rowNum = 2,
    columnSymbol = 'a'
)

val playOutput = PlayOutputModel(
    stone = Stone(
        player = Player.BLACK,
        dot = dot
    ),
    matchState = "ONGOING",
    turn = Player.WHITE
)


val matchService = mockk<MatchService> {
    coEvery { createMatch(any()) } returns matchCreationOutput
    coEvery { getMatchById(match.id) } returns match
    coEvery { play(match.id, dot) } returns playOutput
    coEvery { deleteSetupMatch() } returns Unit
}