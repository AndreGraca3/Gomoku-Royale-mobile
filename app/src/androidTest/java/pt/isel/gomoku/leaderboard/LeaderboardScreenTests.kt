package pt.isel.gomoku.leaderboard

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import leaderBoard
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.http.service.result.APIResult
import pt.isel.gomoku.ui.screens.leaderboard.LeaderBoardScreen
import pt.isel.gomoku.ui.screens.leaderboard.LeaderBoardScreenTag
import pt.isel.gomoku.ui.screens.leaderboard.LeaderboardCardTag

class LeaderboardScreenTests {
    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun leaderboard_screen_displays_leaderboard() {
        // Arrange
        composeTree.setContent {
            LeaderBoardScreen(
                leaderBoard = Loaded(APIResult.success(leaderBoard)),
                onPlayerRequested = {}
            )
        }
        // Act
        // Assert
        composeTree.onNodeWithTag(LeaderBoardScreenTag).assertExists()
    }

    @Test
    fun pressing_any_leaderboardCard_calls_callBack() {
        // Arrange
        var onPlayerRequested = false
        composeTree.setContent {
            LeaderBoardScreen(
                leaderBoard = Loaded(APIResult.success(leaderBoard)),
                onPlayerRequested = { onPlayerRequested = true }
            )
        }
        // Act
        composeTree.onNodeWithTag(LeaderboardCardTag).performClick()
        // Assert
        assertTrue(onPlayerRequested)
    }
}