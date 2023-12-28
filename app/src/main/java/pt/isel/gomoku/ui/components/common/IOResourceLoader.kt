package pt.isel.gomoku.ui.components.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.exceptionOrNull
import pt.isel.gomoku.domain.getOrNull

@Composable
fun <T> IOResourceLoader(
    resource: IOState<T>,
    onCancelRequested: () -> Unit = {},
    errorContent: @Composable () -> Unit = {},
    content: @Composable (T) -> Unit,
) {
    when (resource) {
        is Loading -> {
            LoadingDots(onCancelRequested)
        }

        else -> {
            val ex = resource.exceptionOrNull()
            if (ex != null) {
                Toast.makeText(
                    LocalContext.current,
                    ex.message,
                    Toast.LENGTH_LONG
                ).show()
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