package pt.isel.gomoku.ui.screens.signup

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
import pt.isel.gomoku.http.model.user.UserCredentialsInput
import pt.isel.gomoku.http.service.interfaces.UserService

class SignUpScreenViewModel(private val userService: UserService) : ViewModel() {

    companion object {
        fun factory(userService: UserService) = viewModelFactory {
            initializer { SignUpScreenViewModel(userService) }
        }
    }

    private val loginStateFlow: MutableStateFlow<IOState<Unit>> = MutableStateFlow(idle())

    val loginState: Flow<IOState<Unit>>
        get() = loginStateFlow.asStateFlow()

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun createToken() {
        loginStateFlow.value = loading()
        viewModelScope.launch {
            val result = kotlin.runCatching {
                userService.createToken(UserCredentialsInput(email, password))
            }
            loginStateFlow.value = loaded(result)
            Log.v("login", "result: $result")
        }
    }
}