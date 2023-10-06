package pt.isel.gomoku.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.model.BOARD_DIM

@Preview
@Composable
fun Cross() {
    VerticalLine()
    HorizontalLine()
}

@Composable
fun VerticalLine() {
    Canvas(
        modifier = Modifier
            .size(24.dp)
    ) {
        drawLine(
            color = Color.Black,
            start = Offset(x = size.width / 2F, y = size.height),
            end = Offset(x = size.width / 2F, y = 0F),
            strokeWidth = 2F
        )
    }
}

@Composable
fun HorizontalLine() {
    Canvas(
        modifier = Modifier
            .size(24.dp)
    ) {
        drawLine(
            color = Color.Black,
            start = Offset(x = 0F, y = size.height / 2F),
            end = Offset(x = size.width, y = size.height / 2F),
            strokeWidth = 2F
        )
    }
}