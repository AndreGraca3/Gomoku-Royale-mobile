package pt.isel.gomoku.ui.screens.profile

import android.util.Log
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
import pt.isel.gomoku.domain.loaded
import pt.isel.gomoku.domain.loading
import pt.isel.gomoku.http.model.UserDetails
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

    private val userDetailsFlow: MutableStateFlow<IOState<UserDetails>> = MutableStateFlow(idle())

    val userDetails: Flow<IOState<UserDetails>>
        get() = userDetailsFlow.asStateFlow()

    // TODO: can i use this screen to both fetch the authenticated user(ability to edit) and some other user by id?(leaderboard)

    fun fetchUserDetails() {
        Log.v("profile", "fetching user details")
        userDetailsFlow.value = loading()
        viewModelScope.launch {
            val result = kotlin.runCatching { userService.getAuthenticatedUser() }
            if (result.isFailure) {
                Log.v("profile", "result failed, removing token from local storage")
            }
            userDetailsFlow.value = loaded(result)
            Log.v("login", "result of fetchAuth: $result")
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenRepository.updateOrRemoveLocalToken(null)
        }
    }
}