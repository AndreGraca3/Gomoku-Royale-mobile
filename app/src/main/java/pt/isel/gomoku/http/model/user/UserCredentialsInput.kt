package pt.isel.gomoku.http.model.user

data class UserCredentialsInput(
    val email: String,
    val password: String
)