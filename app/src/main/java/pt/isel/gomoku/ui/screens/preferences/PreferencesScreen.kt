package pt.isel.gomoku.ui.screens.preferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.exceptionOrNull
import pt.isel.gomoku.http.model.MatchCreationOutputModel
import pt.isel.gomoku.http.service.result.APIException
import pt.isel.gomoku.ui.components.buttons.ScaledButton
import pt.isel.gomoku.ui.components.buttons.SquareButton
import pt.isel.gomoku.ui.components.common.LoadingDots
import pt.isel.gomoku.ui.components.layouts.RoundedLayout
import pt.isel.gomoku.ui.components.text.TextShimmer
import pt.isel.gomoku.ui.theme.GomokuTheme
import pt.isel.gomoku.ui.theme.Green
import pt.isel.gomoku.ui.theme.Yellow

const val PreferencesScreenTag = "PreferencesScreenTag"

@Composable
fun PreferencesScreen(
    matchState: IOState<MatchCreationOutputModel>,
    sizes: List<Int?>,
    variants: List<String?>,
    sizeSelected: Int?,
    variantSelected: String?,
    onSizeSelectRequested: (Int?) -> Unit,
    onVariantSelectRequested: (String?) -> Unit,
    onCreateMatchRequested: () -> Unit,
) {
    GomokuTheme(background = R.drawable.grid_background) {
        RoundedLayout {
            TextShimmer(
                text = "Choose your style",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Yellow,
            )

            matchState.exceptionOrNull()?.let {
                if (it is APIException)
                    SweetError(message = it.problem.detail, contentAlignment = Alignment.TopCenter)
            }

            Column(
                modifier = Modifier.height(16.dp)
            ) {
                if (matchState is Loading) {
                    LoadingDots(message = "Creating match...")
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                sizes.map {
                    SquareButton(
                        text = if (it != null) "$it" else "?",
                        onClick = { onSizeSelectRequested(it) },
                        isSelected = sizeSelected == it,
                        squareSize = 65.dp,
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                variants.map {
                    SquareButton(
                        text = it ?: "?",
                        onClick = { onVariantSelectRequested(it) },
                        isSelected = variantSelected == it,
                        squareSize = 150.dp,
                    )
                }
            }

            ScaledButton(
                text = "Search opponent",
                enabled = matchState !is Loading,
                color = Green,
                shape = ShapeDefaults.Large,
                onClick = onCreateMatchRequested
            )
        }
    }
}