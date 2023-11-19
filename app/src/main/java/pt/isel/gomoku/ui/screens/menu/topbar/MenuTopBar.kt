package pt.isel.gomoku.ui.screens.menu.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.Avatar
import pt.isel.gomoku.ui.components.text.TextShimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar() {
    TopAppBar(
        modifier = Modifier.shadow(50.dp, ambientColor = Color.Black, spotColor = Color.Black),
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF4E2608)),
        title = { TextShimmer(ContextCompat.getString(LocalContext.current, R.string.app_name)) },
        actions = {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .pointerInput(Unit) {

                    }
            ) {
                Avatar()
            }
        }
    )
}