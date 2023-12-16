package pt.isel.gomoku.ui.screens.menu.playlist

data class Playlist(
    val name: String,
    val image: Int,
    val disabledImage: Int,
    val onClick: () -> Unit
)