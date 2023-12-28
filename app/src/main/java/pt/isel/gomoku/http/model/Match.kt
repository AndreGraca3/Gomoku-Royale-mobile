package pt.isel.gomoku.http.model

import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.match.Player

data class MatchCreationInputModel(
    val isPrivate: Boolean,
    val size: Int?,
    val variant: String?,
)

data class MatchCreationOutputModel(
    val id: String,
    val state: MatchState,
)

enum class MatchState {
    SETUP,
    ONGOING,
    FINISHED;

    companion object {
        fun fromString(state: String): MatchState = valueOf(state)
    }
    override fun toString() = name
}

data class PlayOutputModel(val stone: Stone, val matchState: String, val turn: Player)