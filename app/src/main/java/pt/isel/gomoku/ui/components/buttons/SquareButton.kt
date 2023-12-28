package pt.isel.gomoku.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import pt.isel.gomoku.ui.theme.Bronze
import pt.isel.gomoku.ui.theme.Green

@Composable
fun SquareButton(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    squareSize: Dp,
    textSize: TextUnit
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = buttonColors(if (isSelected) Green else Bronze),
        modifier = Modifier.size(squareSize, squareSize)
    ) {
        Text(
            text = text,
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}