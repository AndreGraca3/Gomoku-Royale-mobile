package pt.isel.gomoku.leaderboard

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.ui.screens.leaderboard.LeaderBoardActivity
import pt.isel.gomoku.ui.screens.leaderboard.LeaderboardCardTag
import pt.isel.gomoku.ui.screens.userDetails.UserDetailsScreenTag
import pt.isel.gomoku.utils.createPreserveDefaultDependenciesComposeRuleNoActivity

class LeaderboardActivityTests {
    @get:Rule
    val testRule = createPreserveDefaultDependenciesComposeRuleNoActivity()

    @Test
    fun pressing_leaderboard_card_navigates_to_user_details() {
        // Arrange
        ActivityScenario.launch(LeaderBoardActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(LeaderboardCardTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(UserDetailsScreenTag).assertExists()
        }
    }
}