package pt.isel.gomoku.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.ui.theme.Brown
import pt.isel.gomoku.ui.theme.DarkBrown

@Composable
fun Displayer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .background(Brown, RoundedCornerShape(12.dp))
            .border(8.dp, DarkBrown, RoundedCornerShape(12.dp))
            .padding(24.dp)
    ) {
        content()
    }
}