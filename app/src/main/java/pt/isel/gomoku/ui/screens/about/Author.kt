package pt.isel.gomoku.ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.gomoku.R

@Composable
fun Author(name: String, avatarId: Int?, onSendEmailRequested: () -> Unit = { }) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onSendEmailRequested() }
    ) {
        Image(
            painter = painterResource(id = avatarId ?: R.drawable.user_icon),
            contentDescription = null,
            contentScale = androidx.compose.ui.layout.ContentScale.Fit,
            modifier = Modifier
                .size(90.dp)
                .border(2.dp, Color.White, CircleShape)
                .clip(CircleShape)
        )
        Text(name)
        Icon(imageVector = Icons.Default.Email, contentDescription = null)
    }
}

@Preview
@Composable
fun AuthorPreview() {
    MaterialTheme {
        Author("Author", null)
    }
}