package pt.isel.gomoku.ui.screens.login

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
import pt.isel.gomoku.http.model.Problem
import pt.isel.gomoku.http.model.UserCredentialsInputModel
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.http.service.result.runCatchingAPIFailure

class LoginScreenViewModel(private val userService: UserService) : ViewModel() {

    companion object {
        fun factory(userService: UserService) = viewModelFactory {
            initializer { LoginScreenViewModel(userService) }
        }
    }

    private val loginPhaseFlow: MutableStateFlow<IOState<Unit>> =
        MutableStateFlow(idle())

    val loginPhase: Flow<IOState<Unit>>
        get() = loginPhaseFlow.asStateFlow()

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun createToken() {
        loginPhaseFlow.value = loading()
        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                userService.createToken(UserCredentialsInputModel(email, password))
            }
            loginPhaseFlow.value = loaded(result)
            Log.v("login", "result: $result")
        }
    }
}