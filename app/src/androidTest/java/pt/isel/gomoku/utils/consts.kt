import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.domain.game.match.Player
import pt.isel.gomoku.domain.stats.Rank
import pt.isel.gomoku.http.model.LeaderBoard
import pt.isel.gomoku.http.model.MatchCreationOutputModel
import pt.isel.gomoku.http.model.MatchState
import pt.isel.gomoku.http.model.MatchesStats
import pt.isel.gomoku.http.model.PlayOutputModel
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.http.model.UserIdOutputModel
import pt.isel.gomoku.http.model.UserInfo
import pt.isel.gomoku.http.model.UserItem
import pt.isel.gomoku.http.model.UserStatsOutputModel
import pt.isel.gomoku.http.model.WinStats

val userIdOutput = UserIdOutputModel(
    id = 1
)

val user1 = UserInfo(
    id = userIdOutput.id,
    name = "Diogo",
    avatarUrl = null,
    role = "user",
    createdAt = "2021-04-01T00:00:00.000Z",
    rank = Rank(
        name = "Champion",
        iconUrl = "Champion_url_icon"
    )
)

val user2 = UserInfo(
    id = 2,
    name = "André",
    avatarUrl = null,
    role = "user",
    createdAt = "2021-04-01T00:00:00.000Z",
    rank = Rank(
        name = "Champion",
        iconUrl = "Champion_url_icon"
    )
)

val authUser = UserDetails(
    id = 1,
    name = "Diogo",
    email = "diogo@gmail.com",
    avatarUrl = null,
    role = "user",
    createdAt = "2021-04-01T00:00:00.000Z",
)

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

val dot = Dot(
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