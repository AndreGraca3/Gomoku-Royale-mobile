package pt.isel.gomoku.ui.components.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.ui.theme.Bronze
import pt.isel.gomoku.ui.theme.Green
import pt.isel.gomoku.ui.theme.Yellow

@Composable
fun SquareButton(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    squareSize: Dp,
) {
    val scale by animateFloatAsState(if (isSelected) 1.1F else 1F, label = "")
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = buttonColors(if (isSelected) Yellow else Bronze),
        modifier = Modifier.size(squareSize).scale(scale)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1
        )
    }
}