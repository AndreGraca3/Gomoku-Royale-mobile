package pt.isel.gomoku.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.R

val mainFont = FontFamily(
    Font(R.font.burbank, FontWeight.Normal),
    Font(R.font.burbank_bold, FontWeight.Bold),
)

val woodFont = FontFamily(
    Font(R.font.driftwood, FontWeight.Normal),
)

val handFont = FontFamily(
    Font(R.font.overthink, FontWeight.Normal),
)

// Set of Material typography styles to start with
val textStyle = TextStyle(
    fontFamily = mainFont,
    color = Color.White,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val Typography = Typography(
    bodyLarge = textStyle.copy(fontSize = 20.sp, color = Color.White),
    bodyMedium = textStyle,
    bodySmall = textStyle.copy(fontSize = 14.sp, color = Color.White),
)