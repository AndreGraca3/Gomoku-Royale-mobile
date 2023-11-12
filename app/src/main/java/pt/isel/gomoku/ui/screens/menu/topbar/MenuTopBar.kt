package pt.isel.gomoku.ui.screens.menu.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.Avatar
import pt.isel.gomoku.ui.screens.menu.topbar.TitleShimmer
import pt.isel.gomoku.ui.theme.driftWoodFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF4E2608)),
        title = { TitleShimmer() },
        /*navigationIcon = {
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "top_bar_back"
                )
            }
        }*/
        actions = {
            Avatar()
        }
    )
}