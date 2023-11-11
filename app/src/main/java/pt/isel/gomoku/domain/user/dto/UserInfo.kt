package pt.isel.gomoku.domain.user.dto

data class UserInfo(
    val id: Int,
    val name: String,
    val avatarUrl: String?,
    val role: String,
    val rank: String
)