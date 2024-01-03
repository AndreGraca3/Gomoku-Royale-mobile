package pt.isel.gomoku.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.R
import pt.isel.gomoku.utils.bounceClick
import pt.isel.gomoku.utils.playSound

@Composable
fun ScaledButton(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(16.dp),
    onClick: () -> Unit
) {
    val ctx = LocalContext.current
    var textColor by remember { mutableStateOf(Color.Black) }
    var containerColor by remember { mutableStateOf(color) }
    val onOut = {
        textColor = Color.Black
        containerColor = color
    }
    Button(
        onClick = {
            onClick()
            ctx.playSound(R.raw.ui_click_2)
        },
        shape = shape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) containerColor else Gray,
        ),
        border = BorderStroke(2.dp, if(enabled) color else Gray),
        modifier = if (enabled) modifier.bounceClick(
            onClickIn = {
                textColor = color
                containerColor = Color.Transparent
                ctx.playSound(R.raw.ui_highlight)
            },
            onCancel = onOut
        ) else modifier
    ) {
        Text(text = text, fontWeight = FontWeight.Bold, color = textColor, fontSize = 18.sp)
    }
}