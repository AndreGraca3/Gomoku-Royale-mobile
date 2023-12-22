package pt.isel.gomoku.ui.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import pt.isel.gomoku.http.model.user.UserCredentialsInput
import pt.isel.gomoku.http.service.interfaces.UserService

class LoginScreenViewModel(private val userService: UserService) : ViewModel() {

    companion object {
        fun factory(userService: UserService) = viewModelFactory {
            initializer { LoginScreenViewModel(userService) }
        }
    }

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun createToken() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                userService.createToken(UserCredentialsInput(email, password))
            }
            Log.v("login", "result: $result")
        }
    }
}