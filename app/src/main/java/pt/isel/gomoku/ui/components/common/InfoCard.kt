package pt.isel.gomoku.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
fun InfoCard(title: String, value: String? = null, content: @Composable () -> Unit = {}) {
    val borderColor = Color.Gray
    val borderWidth = 2.dp
    val shape = RoundedCornerShape(8.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(borderWidth, borderColor, shape)
            .height(100.dp)
            .widthIn(100.dp, 200.dp)
            .padding(4.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                modifier = Modifier
                    .fillMaxHeight(0.8F)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                content()
                value?.let {
                    TruncatedText(
                        text = it,
                        fontSize = 18.sp,
                    )
                }
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