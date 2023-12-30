package pt.isel.gomoku.domain.user

data class User(
    val id: Int,
    val name: String,
    val avatar: String?,
    val rank: String
)