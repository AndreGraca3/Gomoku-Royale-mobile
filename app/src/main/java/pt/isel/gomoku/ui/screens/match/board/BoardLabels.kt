package pt.isel.gomoku.ui.screens.match.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BoardLetters(size: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0 until size) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                textAlign = TextAlign.Center,
                text = "${'a' + i}"
            )
        }
    }
}

@Composable
fun BoardNumbers(size: Int) {
    Column(
        modifier = Modifier
            .size(24.dp)
    ) {
        for (i in 1 .. size) {
            Text(
                textAlign = TextAlign.Center,
                text = "$i",
            )
        }
    }
}