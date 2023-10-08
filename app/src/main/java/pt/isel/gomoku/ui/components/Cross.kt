package pt.isel.gomoku.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun Cross(color: Color) {
    VerticalLine(color)
    CenterDot(color)
    HorizontalLine(color)
}

@Composable
fun CenterDot(color: Color) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawCircle(
            color = color,
            radius = 4F,
            center = Offset(x = size.width / 2F, y = size.height / 2F)
        )
    }
}

@Composable
fun VerticalLine(color: Color) {
    Canvas(
        modifier = Modifier.fillMaxSize().background(Color.Transparent)
    ) {
        drawLine(
            color = color,
            start = Offset(x = size.width / 2F, y = size.height),
            end = Offset(x = size.width / 2F, y = 0F),
            strokeWidth = 4F
        )
    }
}

@Composable
fun HorizontalLine(color: Color) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawLine(
            color = color,
            start = Offset(x = 0F, y = size.height / 2F),
            end = Offset(x = size.width, y = size.height / 2F),
            strokeWidth = 4F
        )
    }
}