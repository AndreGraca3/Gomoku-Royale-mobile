package pt.isel.gomoku.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.http.model.user.UserInfo
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.repository.interfaces.TokenRepository

class ProfileScreenViewModel(
    private val userService: UserService,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    companion object {
        fun factory(userService: UserService, tokenRepository: TokenRepository) = viewModelFactory {
            initializer { ProfileScreenViewModel(userService, tokenRepository) }
        }
    }

    private val userInfoFlow: MutableStateFlow<IOState<UserInfo?>> = MutableStateFlow(idle())

    val userInfo: Flow<IOState<UserInfo?>>
        get() = userInfoFlow.asStateFlow()

    // TODO: can i use this screen to both fetch the authenticated user(ability to edit) and some other user by id?(leaderboard)

    fun logout() {
        viewModelScope.launch {
            tokenRepository.updateOrRemoveLocalToken(null)
        }
    }
}