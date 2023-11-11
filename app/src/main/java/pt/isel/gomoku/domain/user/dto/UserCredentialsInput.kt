package pt.isel.gomoku.domain.user.dto

data class UserCredentialsInput(
    val email: String,
    val password: String
)