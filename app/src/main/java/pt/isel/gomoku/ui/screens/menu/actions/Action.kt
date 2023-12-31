package pt.isel.gomoku.ui.screens.menu.actions

data class Action(val name: String, val icon: Int, val onClick: () -> Unit, val testTag: String)