package pt.isel.gomoku.ui.screens.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R

@Composable
fun BlackBoard(text: String, color: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(
                vertical = 20.dp
            )
            .paint(
                painter = painterResource(id = R.drawable.black_board),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Text(
            text = text,
            color = color,
            fontFamily = FontFamily(Font(R.font.eraser))
        )
    }
}
