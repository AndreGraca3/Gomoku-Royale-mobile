package pt.isel.gomoku.ui.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.domain.loaded
import pt.isel.gomoku.domain.loading
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.http.service.result.runCatchingAPIFailure
import pt.isel.gomoku.repository.interfaces.TokenRepository

class MainScreenViewModel(
    private val userService: UserService,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    companion object {
        fun factory(userService: UserService, tokenRepository: TokenRepository) = viewModelFactory {
            initializer { MainScreenViewModel(userService, tokenRepository) }
        }
    }

    private val authUserFlow: MutableStateFlow<IOState<UserDetails?>> = MutableStateFlow(idle())

    val authUser: Flow<IOState<UserDetails?>>
        get() = authUserFlow.asStateFlow()

    fun fetchAuthenticatedUser() {
        authUserFlow.value = loading()
        viewModelScope.launch {
            val result = runCatchingAPIFailure { userService.getAuthenticatedUser() }
            if (result.exceptionOrNull()?.problem?.status == 401) {
                tokenRepository.updateOrRemoveLocalToken(null)
                authUserFlow.value = idle()
                return@launch
            }
            authUserFlow.value = loaded(result)
        }
    }
}