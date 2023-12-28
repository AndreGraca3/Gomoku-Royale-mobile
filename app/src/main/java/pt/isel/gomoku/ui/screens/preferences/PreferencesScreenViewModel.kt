package pt.isel.gomoku.ui.screens.preferences

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import pt.isel.gomoku.http.model.MatchCreationInputModel
import pt.isel.gomoku.http.service.interfaces.MatchService

class PreferencesScreenViewModel(
    private val matchService: MatchService
) : ViewModel() {

    companion object {
        fun factory(matchService: MatchService) = viewModelFactory {
            initializer { PreferencesScreenViewModel(matchService) }
        }
    }

    val sizes = listOf(15, 19, null)
    val variants = listOf("FreeStyle", null)

    var size by mutableStateOf<Int?>(null)
    var variant by mutableStateOf<String?>(null)

    fun createMatch(isPrivate: Boolean) {
        viewModelScope.launch {
            matchService.createMatch(
                MatchCreationInputModel(
                    isPrivate = isPrivate,
                    size = size,
                    variant = variant
                )
            )
        }
    }
}