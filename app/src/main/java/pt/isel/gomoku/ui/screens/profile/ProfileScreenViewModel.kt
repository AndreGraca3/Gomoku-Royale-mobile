package pt.isel.gomoku.ui.screens.profile

import android.content.ContentResolver
import android.net.Uri
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
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.http.model.UserUpdateInputModel
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.http.service.result.APIResult
import pt.isel.gomoku.http.service.result.runCatchingAPIFailure
import pt.isel.gomoku.repository.interfaces.TokenRepository
import pt.isel.gomoku.utils.convertImageToBase64

class ProfileScreenViewModel(
    private val contentResolver: ContentResolver,
    private val userService: UserService,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    companion object {
        fun factory(
            contentResolver: ContentResolver,
            userService: UserService,
            tokenRepository: TokenRepository
        ) = viewModelFactory {
            initializer { ProfileScreenViewModel(contentResolver, userService, tokenRepository) }
        }
    }

    var name by mutableStateOf("")

    private val userDetailsFlow: MutableStateFlow<IOState<UserDetails>> =
        MutableStateFlow(idle())

    val userDetails: Flow<IOState<UserDetails>>
        get() = userDetailsFlow.asStateFlow()

    var avatarPath by mutableStateOf<Uri?>(null) // could be a flow?

    fun initializeUserDetails(userDetails: UserDetails) {
        userDetailsFlow.value = loaded(APIResult.success(userDetails))
    }

    fun updateUser() {
        userDetailsFlow.value = loading()

        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                userService.updateUser(
                    UserUpdateInputModel(
                        name,
                        avatarPath?.let { uri -> convertImageToBase64(contentResolver, uri) }
                    )
                )
            }
            userDetailsFlow.value = loaded(result)
        }
    }

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            runCatchingAPIFailure {
                userService.deleteToken()
            }
            tokenRepository.updateOrRemoveLocalToken(null)
            onLogout()
        }
    }
}