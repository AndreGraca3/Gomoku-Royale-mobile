package pt.isel.gomoku.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.buttons.RoundButton
import pt.isel.gomoku.ui.components.text.TextBox

@Composable
fun LoginScreen(onLoginRequest: () -> Unit, onSignInRequest: () -> Unit) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.floor_background),
        contentScale = ContentScale.FillBounds,
        contentDescription = "Floor background"
    )

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BlackBoard(text = "LOGIN", color = Color.White)

        Box(
            modifier = Modifier
                .paint(
                    painter = painterResource(id = R.drawable.main_screen),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextBox(title = "Email", text = email)

                TextBox(
                    title = "Password",
                    text = password,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }
        }

        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            RoundButton(
                onClick = onLoginRequest
            ) {
                Text("Login")
            }
        }

        Row {
            RoundButton(
                onClick = onSignInRequest
            ) {
                Text("Sign up")
            }
        }

    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen({}, {})
}