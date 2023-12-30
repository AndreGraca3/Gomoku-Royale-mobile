package pt.isel.gomoku.ui.screens.userDetails

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
import pt.isel.gomoku.http.model.UserInfo
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.http.service.result.runCatchingAPIFailure

class UserDetailsScreenViewModel(
    private val userService: UserService,
) : ViewModel() {

    companion object {
        fun factory(userService: UserService) = viewModelFactory {
            initializer { UserDetailsScreenViewModel(userService) }
        }
    }

    private val userDetailsFlow: MutableStateFlow<IOState<UserInfo>> =
        MutableStateFlow(idle())

    val userDetails: Flow<IOState<UserInfo>>
        get() = userDetailsFlow.asStateFlow()

    fun fetchUser(id: Int) {
        userDetailsFlow.value = loading()
        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                userService.getUser(
                    id
                )
            }
            userDetailsFlow.value = loaded(result)
        }
    }
}