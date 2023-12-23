package pt.isel.gomoku.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.ui.theme.mainFont

@Composable
fun TruncatedText(
    text: String,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.White,
    fontFamily: FontFamily = mainFont,
    maxLines: Int = 1
) {
    Text(
        textAlign = TextAlign.Center,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        color = color,
        fontSize = fontSize,
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
    )
}