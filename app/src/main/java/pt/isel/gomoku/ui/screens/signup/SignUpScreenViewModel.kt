package pt.isel.gomoku.ui.screens.signup

import android.content.ContentResolver
import android.net.Uri
import android.util.Base64
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
import pt.isel.gomoku.http.model.UserCreationInputModel
import pt.isel.gomoku.http.model.UserIdOutputModel
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.http.service.result.runCatchingAPIFailure
import pt.isel.gomoku.utils.convertImageToBase64
import java.io.InputStream


class SignUpScreenViewModel(
    private val userService: UserService,
    private val contentResolver: ContentResolver
) : ViewModel() {

    companion object {
        fun factory(userService: UserService, contentResolver: ContentResolver) = viewModelFactory {
            initializer { SignUpScreenViewModel(userService, contentResolver) }
        }
    }

    private val signUpPhaseFlow: MutableStateFlow<IOState<UserIdOutputModel>> =
        MutableStateFlow(idle())

    val signUpPhase: Flow<IOState<UserIdOutputModel>>
        get() = signUpPhaseFlow.asStateFlow()

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var avatarPath: Uri? by mutableStateOf(null)

    fun createUser() {
        signUpPhaseFlow.value = loading()
        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                userService.createUser(
                    UserCreationInputModel(
                        name,
                        email,
                        password,
                        avatarPath?.let { uri -> convertImageToBase64(contentResolver, uri) }
                    )
                )
            }
            signUpPhaseFlow.value = loaded(result)
        }
    }
}