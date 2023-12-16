package pt.isel.gomoku.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.ui.components.text.GTextField
import pt.isel.gomoku.ui.theme.DarkBrown
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.ui.theme.Green
import pt.isel.gomoku.ui.theme.Yellow

@Composable
fun LoginScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginRequest: () -> Unit,
    onSignUpRequest: () -> Unit
) {
    GomokuTheme {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier
                        .background(DarkBrown, RoundedCornerShape(12.dp))
                        .padding(28.dp)
                        .fillMaxWidth(0.8F)
                ) {

                    Text(
                        text = "Welcome back \uD83D\uDC4B",
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )

                    GTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        leadingIcon = {
                            Text(text = "📧")
                        },
                        placeholder = { Text(text = "Email") },
                    )

                    GTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        leadingIcon = {
                            Text(text = "🔑")
                        },
                        placeholder = { Text(text = "Password") },
                        isPassword = true,
                    )

                    /*error?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }*/

                    AuthButton(text = "Login", color = Yellow, onClick = onLoginRequest)
                    AuthButton(
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
    LoginScreen("", "", {}, {}, {}, {})
}