package pt.isel.gomoku.ui.screens.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.ui.screens.login.LoginActivity
import pt.isel.gomoku.utils.NavigateAux

class SignUpActivity : ComponentActivity() {

    private val vm by viewModels<SignUpScreenViewModel> {
        SignUpScreenViewModel.factory(
            (application as DependenciesContainer).userService, contentResolver
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            vm.signUpPhase.collect {
                if (it is Loaded && it.value.isSuccess) {
                    NavigateAux.navigateTo<LoginActivity>(this@SignUpActivity)
                    finish()
                }
            }
        }

        setContent {
            val launcher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { uri -> vm.avatarPath = uri }
                )

            val phase = vm.signUpPhase.collectAsState(initial = idle())

            SignUpScreen(
                phaseState = phase.value,
                name = vm.name,
                email = vm.email,
                password = vm.password,
                avatarUrl = vm.avatarPath,
                onNameChange = { vm.name = it },
                onEmailChange = { vm.email = it },
                onPasswordChange = { vm.password = it },
                onAvatarChange = { launcher.launch("image/*") },
                onSignUpRequested = { vm.createUser() }
            )
        }
    }
}