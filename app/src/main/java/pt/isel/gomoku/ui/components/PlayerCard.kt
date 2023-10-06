package pt.isel.gomoku.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R
import pt.isel.gomoku.model.User

@Composable
fun PlayerCard() {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.user_icon),
            contentDescription = null,
            modifier = Modifier.border(2.dp, Color.Green, shape = CircleShape)
                .size(100.dp)
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawRoundRect(
                color = Color.LightGray,
                cornerRadius = CornerRadius(15F, 15F),
                size = Size(size.width * 0.38F, size.height * 0.1F),
                topLeft = Offset(x = size.width * 0.2F, y = size.height * 0.1F)
            )
            drawCircle(
                color = Color.Gray,
                radius = size.height * 0.1F / 2,
                center = Offset(x = size.width * 0.2F + 40F, y = size.height * 0.1F + 40F)
            )
        }

    }
}

@Composable
fun PlayerCard(
    user: User = User("Diogo", null, 1000)
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.size(100.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.user_icon),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .border(1.dp, Color.Green, shape = CircleShape)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .size(40.dp, 20.dp)
                .background(Color.LightGray, shape = RectangleShape)
        ) {
            Text(
                text = "Diogo",
                modifier = Modifier.size(10.dp, 10.dp)
            )
            Text("1000")
        }
    }
}