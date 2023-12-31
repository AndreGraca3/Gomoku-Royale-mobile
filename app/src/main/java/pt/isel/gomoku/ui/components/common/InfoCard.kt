package pt.isel.gomoku.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.text.TruncatedText

@Composable
fun InfoCard(value: String = "", title: String, content: @Composable () -> Unit = {}) {
    val borderColor = Color.Gray
    val borderWidth = 2.dp
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = Modifier
            .border(borderWidth, borderColor, shape)
            .height(100.dp)
            .width(150.dp)
            .padding(4.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                content()
                TruncatedText(
                    text = value,
                    fontSize = 18.sp,
                )
            }
            TruncatedText(
                text = title,
                color = Color.Gray,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview
@Composable
private fun InfoDisplayerPreview() {
    InfoCard(
        title = "Silver", content = {
            Image(painter = painterResource(id = R.drawable.silver), contentDescription = null)
        }
    )
}