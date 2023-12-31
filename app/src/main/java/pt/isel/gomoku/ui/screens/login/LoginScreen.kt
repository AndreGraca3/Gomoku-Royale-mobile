package pt.isel.gomoku.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.ui.components.buttons.ScaledButton
import pt.isel.gomoku.ui.components.layouts.Displayer
import pt.isel.gomoku.ui.components.common.LoadingDots
import pt.isel.gomoku.ui.components.text.GomokuTextField
import pt.isel.gomoku.ui.components.text.TruncatedText
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.ui.theme.Green
import pt.isel.gomoku.ui.theme.Yellow

@Composable
fun LoginScreen(
    phaseState: IOState<Unit> = Idle,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginRequest: () -> Unit,
    onSignUpRequest: () -> Unit
) {
    GomokuTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            Displayer {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(0.8F),
                ) {
                    Text(
                        text = "Welcome back \uD83D\uDC4B",
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )

                    GomokuTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        leadingIcon = {
                            Text(text = "📧")
                        },
                        placeholder = { Text(text = "Email") },
                    )

                    GomokuTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        leadingIcon = {
                            Text(text = "🔑")
                        },
                        placeholder = { Text(text = "Password") },
                        isPassword = true,
                    )

                    Column(
                        modifier = Modifier.height(60.dp),
                    ) {
                        when (phaseState) {
                            is Loading -> {
                                LoadingDots(message = "Fetching your stone...")
                            }

                            is Loaded -> {
                                val isSuccess = phaseState.value.isSuccess
                                TruncatedText(
                                    text =
                                    if (isSuccess) "Login successful"
                                    else phaseState.value.exceptionOrNull()?.message
                                        ?: "Login failed",
                                    color = if (isSuccess) Color.Green else Color.Red,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = TextUnit.Unspecified,
                                    maxLines = 2,
                                )
                            }

                            else -> {}
                        }
                    }

                    ScaledButton(text = "Login", color = Yellow, onClick = onLoginRequest)
                    ScaledButton(
                        text = "Sign Up",
                        color = Green,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onSignUpRequest
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        email = "",
        password = "",
        onEmailChange = {},
        onPasswordChange = {},
        onLoginRequest = {},
        onSignUpRequest = {})
}