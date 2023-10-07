package pt.isel.gomoku.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.gomoku.ui.screens.board.gridColor

@Preview
@Composable
fun Cross() {
    VerticalLine()
    CenterDot()
    HorizontalLine()
}

@Composable
fun CenterDot() {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawCircle(
            color = gridColor,
            radius = 4F,
            center = Offset(x = size.width / 2F, y = size.height / 2F)
        )
    }
}

@Composable
fun VerticalLine() {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawLine(
            color = gridColor,
            start = Offset(x = size.width / 2F, y = size.height),
            end = Offset(x = size.width / 2F, y = 0F),
            strokeWidth = 4F
        )
    }
}

// fit parent
@Composable
fun HorizontalLine() {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawLine(
            color = gridColor,
            start = Offset(x = 0F, y = size.height / 2F),
            end = Offset(x = size.width, y = size.height / 2F),
            strokeWidth = 4F
        )
    }
}