package pt.isel.gomoku.ui.screens.preferences

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.domain.loaded
import pt.isel.gomoku.domain.loading
import pt.isel.gomoku.http.model.MatchCreationInputModel
import pt.isel.gomoku.http.model.MatchCreationOutputModel
import pt.isel.gomoku.http.service.interfaces.MatchService
import pt.isel.gomoku.http.service.result.runCatchingAPIFailure

class PreferencesScreenViewModel(
    private val matchService: MatchService
) : ViewModel() {

    companion object {
        fun factory(matchService: MatchService) = viewModelFactory {
            initializer { PreferencesScreenViewModel(matchService) }
        }
    }

    private val matchStateFlow: MutableStateFlow<IOState<MatchCreationOutputModel>> =
        MutableStateFlow(idle())

    val sizes = listOf(15, 19, null)
    val variants = listOf("FreeStyle", null)

    var size by mutableStateOf<Int?>(null)
    var variant by mutableStateOf<String?>(null)

    val matchState: Flow<IOState<MatchCreationOutputModel>>
        get() = matchStateFlow.asStateFlow()

    fun createMatch(isPrivate: Boolean) {
        matchStateFlow.value = loading()
        viewModelScope.launch {
            val result = runCatchingAPIFailure {
                matchService.createMatch(
                    MatchCreationInputModel(
                        isPrivate = isPrivate,
                        size = size,
                        variant = variant
                    )
                )
            }
            matchStateFlow.value = loaded(result)
            Log.v("create match", "result: $result")
        }
    }
}