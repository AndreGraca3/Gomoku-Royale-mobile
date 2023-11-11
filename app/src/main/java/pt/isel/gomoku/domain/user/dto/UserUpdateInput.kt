package pt.isel.gomoku.domain.user.dto

data class UserUpdateInput(
    val name: String,
    val avatarUrl: String?
)