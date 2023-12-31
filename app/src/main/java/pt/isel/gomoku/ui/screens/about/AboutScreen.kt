package pt.isel.gomoku.ui.screens.about

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import pt.isel.gomoku.R
import pt.isel.gomoku.ui.components.common.Displayer
import pt.isel.gomoku.ui.components.text.TextShimmer
import pt.isel.gomoku.ui.screens.menu.logo.HeartBeatLogo
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.ui.theme.Yellow
import pt.isel.gomoku.utils.clickableWithoutRipple
import pt.isel.gomoku.utils.spin

const val AboutScreenTag = "AboutScreenTag"

@Composable
fun AboutScreen(
    authors: List<AuthorInfo>,
    onSendEmailRequest: (String) -> Unit = {},
    onOpenWebsiteRequest: (Uri) -> Unit = {}
) {
    val ctx = LocalContext.current

    GomokuTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().testTag(AboutScreenTag)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxHeight(0.4F)
            ) {
                HeartBeatLogo(
                    PaddingValues(0.dp),
                    modifier = Modifier
                        .clickableWithoutRipple {
                            onOpenWebsiteRequest(Uri.parse(getString(ctx, R.string.studio_website)))
                        }
                        .fillMaxWidth(0.5F)
                )
                Image(
                    painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clickableWithoutRipple {
                            onOpenWebsiteRequest(Uri.parse(getString(ctx, R.string.app_website)))
                        }
                        .spin(1000, 3000)
                )
            }

            Displayer {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxHeight(0.7F)
                ) {
                    TextShimmer(
                        text = "Developers",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Yellow,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        authors.forEach {
                            Author(it.name, it.avatar) { onSendEmailRequest(it.email) }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen(
        authors = listOf(
            AuthorInfo(
                name = "Author 1",
                email = "d",
            ),
            AuthorInfo(
                name = "Author 2",
                email = "d",
            ),
        )
    )
}