package pt.isel.gomoku.main

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import authUser
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.http.service.result.APIResult
import pt.isel.gomoku.ui.components.common.AvatarTag
import pt.isel.gomoku.ui.screens.menu.MenuScreen
import pt.isel.gomoku.ui.screens.menu.MultiplayerCardTag
import pt.isel.gomoku.ui.screens.menu.PlayCardsTag
import pt.isel.gomoku.ui.screens.menu.actions.MainScreenInfoButtonTag
import pt.isel.gomoku.ui.screens.menu.actions.MainScreenLeaderboardButtonTag
import pt.isel.gomoku.ui.screens.menu.actions.MainScreenSettingsButtonTag
import pt.isel.gomoku.ui.screens.menu.actions.MainScreenStatsButtonTag

class MenuScreenTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun main_screen_displays_user_avatar_if_this_one_is_logged() {
        // Arrange
        composeTree.setContent {
            MenuScreen(
                userInfoState = Loaded(APIResult.success(authUser))
            )
        }
        // Act
        // Assert
        composeTree.onNodeWithTag(AvatarTag).assertExists()
        //composeTree.onNode(SemanticsMatcher.expectValue(userAvatar, authUser.avatarUrl))
        //    .assertIsDisplayed()
    }

    @Test
    fun main_screen_displays_PlayCards() {
        // Arrange
        composeTree.setContent {
            MenuScreen(
                userInfoState = Loaded(APIResult.success(authUser))
            )
        }
        // Act
        // Assert
        composeTree.onNodeWithTag(PlayCardsTag).assertExists()
    }

    @Test
    fun main_screen_displays_buttons_from_bottom_bar() {
        // Arrange
        composeTree.setContent {
            MenuScreen(
                userInfoState = Idle
            )
        }
        // Act
        // Assert
        composeTree.onNodeWithTag(MainScreenLeaderboardButtonTag).assertExists().assertIsEnabled()
        composeTree.onNodeWithTag(MainScreenStatsButtonTag).assertExists().assertIsEnabled()
        composeTree.onNodeWithTag(MainScreenSettingsButtonTag).assertExists().assertIsEnabled()
        composeTree.onNodeWithTag(MainScreenInfoButtonTag).assertExists().assertIsEnabled()
    }

    @Test
    fun pressing_any_button_from_menu_screen_calls_callBack() {
        // Arrange
        var onAvatarClick = false
        var onMatchRequested = false
        var onLeaderBoardRequested = false
        var onAboutRequested = false
        var onStatsRequested = false
        composeTree.setContent {
            MenuScreen(
                userInfoState = Idle,
                onAvatarClick = { onAvatarClick = true },
                onMatchRequested = { onMatchRequested = true },
                onLeaderBoardRequested = { onLeaderBoardRequested = true },
                onAboutRequested = { onAboutRequested = true },
                onStatsRequested = { onStatsRequested = true }
            )
        }
        // Act
        composeTree.onNodeWithTag(AvatarTag).performClick()
        composeTree.onNodeWithTag(MainScreenLeaderboardButtonTag).performClick()
        composeTree.onNodeWithTag(MultiplayerCardTag).performClick()
        //composeTree.onNodeWithTag(PrivateCardTag).performClick()
        composeTree.onNodeWithTag(MainScreenStatsButtonTag).performClick()
        composeTree.onNodeWithTag(MainScreenSettingsButtonTag).performClick()
        composeTree.onNodeWithTag(MainScreenInfoButtonTag).performClick()
        // Assert
        assertTrue(onAvatarClick)
        assertTrue(onMatchRequested)
        assertTrue(onLeaderBoardRequested)
        assertTrue(onAboutRequested)
        assertTrue(onStatsRequested)
    }
}