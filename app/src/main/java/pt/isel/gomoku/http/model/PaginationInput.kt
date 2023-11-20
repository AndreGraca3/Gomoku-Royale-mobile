package pt.isel.gomoku.http.model

data class PaginationInput(
    val skip: Int,
    val limit: Int
)