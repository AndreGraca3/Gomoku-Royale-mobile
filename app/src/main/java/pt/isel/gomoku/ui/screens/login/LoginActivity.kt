package pt.isel.gomoku.ui.screens.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.gomoku.DependenciesContainer

class LoginActivity : ComponentActivity() {

    private val vm by viewModels<LoginScreenViewModel> {
        LoginScreenViewModel.factory((application as DependenciesContainer).userService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                email = vm.email,
                password = vm.password,
                onEmailChange = { vm.email = it },
                onPasswordChange = { vm.password = it },
                onLoginRequest = { vm.createToken() },
                onSignUpRequest = { /*TODO*/ }
            )
        }
    }
}