package pt.isel.gomoku.http.model.user

data class UserUpdateInput(
    val name: String,
    val avatarUrl: String?
)