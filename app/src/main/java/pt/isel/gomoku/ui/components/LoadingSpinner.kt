package pt.isel.gomoku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import pt.isel.gomoku.R

@Composable
fun LoadingSpinner() {
    Column(
        modifier = Modifier
            .size(100.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "spinner",
            contentScale = ContentScale.Fit,
        )
        Text(
            text = "Loading...",
            modifier = Modifier
                .shimmer()
        )
    }
}