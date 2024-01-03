package pt.isel.gomoku.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.http.model.UserInfo
import pt.isel.gomoku.ui.components.common.AsyncAvatar
import pt.isel.gomoku.ui.components.common.IOResourceLoader
import pt.isel.gomoku.ui.components.common.InfoCard
import pt.isel.gomoku.ui.components.layouts.RoundedLayout
import pt.isel.gomoku.ui.components.text.TruncatedText
import pt.isel.gomoku.ui.screens.profile.TimeDisplay
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.utils.getRankIconByName

@Composable
fun UserDetailsScreen(userInfo: IOState<UserInfo>) {
    GomokuTheme(background = R.drawable.grid_background) {
        RoundedLayout {
            IOResourceLoader(resource = userInfo) {
                AsyncAvatar(avatar = it.avatarUrl, size = 150.dp)

                TruncatedText(text = it.name, fontSize = 30.sp)

                Row {
                    InfoCard(title = it.rank.name) {
                        Image(
                            painter = painterResource(id = getRankIconByName(it.rank.name)),
                            contentDescription = null,
                        )
                    }
                }

                TimeDisplay(it.createdAt)
            }
        }
    }
}