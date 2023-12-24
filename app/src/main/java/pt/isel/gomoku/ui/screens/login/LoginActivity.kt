package pt.isel.gomoku.ui.screens.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.ui.screens.signup.SignUpActivity
import pt.isel.gomoku.utils.NavigateAux

class LoginActivity : ComponentActivity() {

    private val vm by viewModels<LoginScreenViewModel> {
        LoginScreenViewModel.factory((application as DependenciesContainer).userService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            vm.loginPhase.collect {
                if (it is Loaded && it.value.isSuccess) {
                    finish()
                }
            }
        }

        setContent {
            val phase = vm.loginPhase.collectAsState(initial = idle())
            LoginScreen(
                phaseState = phase.value,
                email = vm.email,
                password = vm.password,
                onEmailChange = { vm.email = it },
                onPasswordChange = { vm.password = it },
                onLoginRequest = { vm.createToken() },
                onSignUpRequest = { NavigateAux.navigateTo<SignUpActivity>(this) }
            )
        }
    }
}
