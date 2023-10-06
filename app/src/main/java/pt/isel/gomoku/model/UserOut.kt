package pt.isel.gomoku.model

data class User(
    val name: String,
    val avatar: String?,
    val rank: Int
)

val users = listOf(
    User("Diogo", null, 1000),
    User("André", null, 1000)
)