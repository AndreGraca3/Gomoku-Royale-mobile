package pt.isel.gomoku.ui.components.text

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.ui.theme.DarkBrown
import pt.isel.gomoku.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GomokuTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = placeholder,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = DarkBrown,
            focusedBorderColor = Yellow,
            unfocusedBorderColor = Color.White,
            cursorColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if(isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if(isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
    )
}