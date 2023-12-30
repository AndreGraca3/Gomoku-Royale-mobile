package pt.isel.gomoku.ui.screens.userDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.http.model.UserInfo
import pt.isel.gomoku.ui.components.common.IOResourceLoader
import pt.isel.gomoku.ui.screens.profile.AvatarIcon
import pt.isel.gomoku.ui.screens.profile.TimeDisplay
import pt.isel.gomoku.ui.theme.Brown
import pt.isel.gomoku.ui.theme.DarkBrown
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.getRankIconByName

@Composable
fun UserDetailsScreen(
    userInfo: IOState<UserInfo>
) {
    GomokuTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            IOResourceLoader(resource = userInfo) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize(0.8F)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Brown)
                        .border(5.dp, color = DarkBrown, shape = RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "User profile",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )

                    AvatarIcon(
                        avatar = it.avatarUrl,
                        role = it.role,
                        onAvatarChange = {}
                    )

                    Text(
                        text = it.name
                    )

                    TimeDisplay(
                        text = "Playing since:",
                        time = it.createdAt
                    )

                    Row {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Image(
                                painter = painterResource(getRankIconByName(it.rank.name)),
                                contentDescription = "rank"
                            )
                        }
                    }
                }
            }
        }
    }
}