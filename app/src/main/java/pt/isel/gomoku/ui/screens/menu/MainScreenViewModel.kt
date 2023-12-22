package pt.isel.gomoku.ui.screens.menu

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import pt.isel.gomoku.http.model.user.UserInfo
import pt.isel.gomoku.http.service.interfaces.UserService
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

    var hasInitialized by mutableStateOf(false) // defines when to remove the splash screen

    private val userInfoFlow: MutableStateFlow<IOState<UserInfo?>> = MutableStateFlow(idle())

    val userInfo: Flow<IOState<UserInfo?>>
        get() = userInfoFlow.asStateFlow()

    fun fetchAuthenticatedUser() {
        userInfoFlow.value = loading()
        viewModelScope.launch {
            val result = kotlin.runCatching { userService.getAuthenticatedUser() }
            userInfoFlow.value = loaded(result)
            Log.v("login", "result authuser: $result")
        }
    }

    private val tokenFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    val token: Flow<String?>
        get() = tokenFlow.asStateFlow()

    fun getLocalToken() {
        Log.v("login", "getLocalToken")
        viewModelScope.launch {
            tokenFlow.value = tokenRepository.getLocalToken()
        }
    }
}