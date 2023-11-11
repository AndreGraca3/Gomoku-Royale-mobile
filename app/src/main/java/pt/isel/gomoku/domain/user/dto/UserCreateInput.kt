package pt.isel.gomoku.domain.user.dto

data class UserCreateInput(
    val name: String,
    val email: String,
    val password: String,
    val avatarUrl: String?
)