package pt.isel.gomoku.domain.user.dto

data class User(
    val name: String,
    val avatar: String?,
    val rank: String
)