package pt.isel.gomoku.domain.user

import java.time.LocalDateTime

data class Token(
    val tokenValue: String,
    val userId: Int,
    val createdAt: LocalDateTime,
    val lastUsed: LocalDateTime
)