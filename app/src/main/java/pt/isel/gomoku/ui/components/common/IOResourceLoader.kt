package pt.isel.gomoku.ui.components.common

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.exceptionOrNull
import pt.isel.gomoku.domain.getOrNull

@Composable
fun <T> IOResourceLoader(
    resource: IOState<T>,
    loadingMessage: String = "Loading...",
    errorContent: @Composable () -> Unit = {},
    loadingContent: @Composable () -> Unit = {},
    content: @Composable (T) -> Unit,
) {
    when (resource) {
        is Loading -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            ) {
                LoadingDots(
                    message = loadingMessage,
                )
                loadingContent()
            }
        }

        else -> {
            val ex = resource.exceptionOrNull()
            if (ex != null) {
                SweetError(
                    ex.message ?: "Unknown error",
                    Toast.LENGTH_LONG,
                    contentAlignment = Alignment.Center
                )
                errorContent()
            } else {
                val data = resource.getOrNull()
                if (data != null) {
                    content(data)
                }
            }
        }
    }
}