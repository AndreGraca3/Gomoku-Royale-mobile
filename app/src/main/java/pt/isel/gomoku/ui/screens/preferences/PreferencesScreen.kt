package pt.isel.gomoku.ui.screens.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.gomoku.ui.components.buttons.SquareButton
import pt.isel.gomoku.ui.theme.Brown
import pt.isel.gomoku.ui.theme.DarkBrown
import pt.isel.gomoku.ui.theme.GomokuTheme

@Composable
fun PreferencesScreen(
    sizes: List<Int?>,
    variants: List<String?>,
    sizeSelected: Int?,
    variantSelected: String?,
    onSizeSelectRequested: (Int?) -> Unit,
    onVariantSelectRequested: (String?) -> Unit,
    onCreateMatchRequested: () -> Unit
) {
    GomokuTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
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
                    text = "Choose your style",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
                Row {
                    sizes.map {
                        SquareButton(
                            text = if (it != null) "$it" else "?",
                            onClick = { onSizeSelectRequested(it) },
                            isSelected = sizeSelected == it,
                            squareSize = 80.dp,
                            textSize = 27.sp
                        )
                    }
                }

                Row {
                    variants.map {
                        SquareButton(
                            text = it ?: "?",
                            onClick = { onVariantSelectRequested(it) },
                            isSelected = variantSelected == it,
                            squareSize = 150.dp,
                            textSize = 23.sp
                        )
                    }
                }

                Row {
                    Button(
                        onClick = onCreateMatchRequested,
                        colors = buttonColors(Color.Green)
                    ) {
                        Text(
                            text = "Start searching",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}