package pt.isel.gomoku.http.model.user

data class UserDetails(
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String,
    val role: String,
    val createdAt: String,
)