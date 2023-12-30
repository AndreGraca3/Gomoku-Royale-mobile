package pt.isel.gomoku.http.model

import pt.isel.gomoku.domain.stats.Rank

data class UserInfo(
    val id: Int,
    val name: String,
    val avatarUrl: String?,
    val role: String,
    val rank: String
)

data class UserDetails(
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String?,
    val role: String,
    val createdAt: String,
)

data class UserItem(
    val id: Int,
    val name: String,
    val role: String,
    val rank: Rank,
)

data class UserCreationInputModel(
    val name: String,
    val email: String,
    val password: String,
    val avatarUrl: String?
)

data class UserCredentialsInputModel(
    val email: String,
    val password: String
)

data class UserIdOutputModel(val id: Int)

data class UserUpdateInputModel(
    val name: String,
    val avatarUrl: String?
)