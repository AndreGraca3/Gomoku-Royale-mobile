package pt.isel.gomoku.main

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.ui.components.common.AvatarTag
import pt.isel.gomoku.ui.screens.about.AboutScreenTag
import pt.isel.gomoku.ui.screens.leaderboard.LeaderBoardScreenTag
import pt.isel.gomoku.ui.screens.menu.MenuActivity
import pt.isel.gomoku.ui.screens.menu.MenuScreenTag
import pt.isel.gomoku.ui.screens.menu.MultiplayerCardTag
import pt.isel.gomoku.ui.screens.menu.actions.MainScreenInfoButtonTag
import pt.isel.gomoku.ui.screens.menu.actions.MainScreenLeaderboardButtonTag
import pt.isel.gomoku.ui.screens.menu.actions.MainScreenStatsButtonTag
import pt.isel.gomoku.ui.screens.preferences.PreferencesScreenTag
import pt.isel.gomoku.ui.screens.profile.ProfileScreenTag
import pt.isel.gomoku.ui.screens.stats.StatsScreenTag
import pt.isel.gomoku.utils.createPreserveDefaultDependenciesComposeRuleNoActivity

class MenuActivityTests {

    @get:Rule
    val testRule = createPreserveDefaultDependenciesComposeRuleNoActivity()

    @Test
    fun initially_the_menu_screen_is_displayed() {
        // Arrange
        ActivityScenario.launch(MenuActivity::class.java).use {
            // Act
            // Assert
            testRule.onNodeWithTag(MenuScreenTag).assertExists()
        }
    }

    @Test
    fun pressing_avatar_navigates_to_profile_if_user_is_logged() {
        // Arrange
        ActivityScenario.launch(MenuActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(AvatarTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(ProfileScreenTag).assertExists()
        }
    }

    /*@Test
    fun pressing_avatar_navigates_to_login_if_user_is_not_logged() {
        // Arrange
        testApplication.userService = mockk {
            coEvery { getAuthenticatedUser() } returns null
        }

        ActivityScenario.launch(MainActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(PlayButtonTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(UserPreferencesScreenTag).assertExists()
        }
    }*/

    @Test
    fun pressing_play_navigates_to_preferences_if_user_info_exists() {
        // Arrange
        ActivityScenario.launch(MenuActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(MultiplayerCardTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(PreferencesScreenTag).assertExists()
        }
    }

    @Test
    fun pressing_leaderboard_button_navigates_to_leaderBoard() {
        // Arrange
        ActivityScenario.launch(MenuActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(MainScreenLeaderboardButtonTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(LeaderBoardScreenTag).assertExists()
        }
    }

    @Test
    fun pressing_stats_navigates_to_stats_if_user_info_exists() {
        // Arrange
        ActivityScenario.launch(MenuActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(MainScreenStatsButtonTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(StatsScreenTag).assertExists()
        }
    }

    @Test
    fun pressing_about_button_navigates_to_about() {
        // Arrange
        ActivityScenario.launch(MenuActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(MainScreenInfoButtonTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(AboutScreenTag).assertExists()
        }
    }
}