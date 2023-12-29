package pt.isel.gomoku.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import pt.isel.gomoku.http.model.UserUpdateInputModel
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

    var isEditing by mutableStateOf(false)

    var name by mutableStateOf("")
    var avatar by mutableStateOf<String?>(null)

    fun changeToEditMode() {
        isEditing = true
    }

    fun updateUserRequest() {
        viewModelScope.launch {
            userService.updateUser(
                UserUpdateInputModel(
                    name,
                    avatar
                )
            )
        }
        isEditing = false
    }

    fun logout() {
        viewModelScope.launch {
            tokenRepository.updateOrRemoveLocalToken(null)
        }
    }
}