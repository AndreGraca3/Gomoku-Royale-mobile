package pt.isel.gomoku.signup

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.ui.components.common.AvatarTag
import pt.isel.gomoku.ui.screens.login.LoginScreen
import pt.isel.gomoku.ui.screens.login.SignUpButtonTag
import pt.isel.gomoku.ui.screens.signup.SignUpEmailInputTag
import pt.isel.gomoku.ui.screens.signup.SignUpNameInputTag
import pt.isel.gomoku.ui.screens.signup.SignUpPasswordInputTag
import pt.isel.gomoku.ui.screens.signup.SignUpScreen

class SignupScreenTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun signup_screen_displays_three_input_fields() {
        // Arrange
        var onNameChange = false
        var onEmailChange = false
        var onPasswordChange = false
        composeTree.setContent {
            SignUpScreen(
                name = "",
                email = "",
                password = "",
                avatarUrl = null,
                onNameChange = { onNameChange = true },
                onEmailChange = { onEmailChange = true },
                onPasswordChange = { onPasswordChange = true },
                onAvatarChange = {},
                onSignUpRequested = {}
            )
        }
        // Act
        composeTree.onNodeWithTag(SignUpNameInputTag).performTextInput("Diogo")
        composeTree.onNodeWithTag(SignUpEmailInputTag).performTextInput("Diogo@gmail.com")
        composeTree.onNodeWithTag(SignUpPasswordInputTag).performTextInput("123")
        // Assert
        composeTree.onNodeWithTag(SignUpNameInputTag).assertExists().assertIsDisplayed()
        composeTree.onNodeWithTag(SignUpEmailInputTag).assertExists().assertIsDisplayed()
        composeTree.onNodeWithTag(SignUpPasswordInputTag).assertExists().assertIsDisplayed()
        assertTrue(onNameChange)
        assertTrue(onEmailChange)
        assertTrue(onPasswordChange)
    }

    @Test
    fun signup_screen_displays_avatar_button_and_pressing_calls_callback() {
        // Arrange
        var onAvatarChange = false
        composeTree.setContent {
            SignUpScreen(
                name = "",
                email = "",
                password = "",
                avatarUrl = null,
                onNameChange = {},
                onEmailChange = {},
                onPasswordChange = {},
                onAvatarChange = { onAvatarChange = true },
                onSignUpRequested = {}
            )
        }
        // Act
        composeTree.onNodeWithTag(AvatarTag).performClick()
        // Assert
        composeTree.onNodeWithTag(AvatarTag).assertExists().assertIsDisplayed()
        assertTrue(onAvatarChange)
    }

    @Test
    fun signup_screen_displays_signup_button_and_pressing_calls_callback() {
        // Arrange
        var onSignUpRequest = false
        composeTree.setContent {
            LoginScreen(
                email = "",
                password = "",
                onEmailChange = {},
                onPasswordChange = {},
                onLoginRequest = {},
                onSignUpRequest = { onSignUpRequest = true }
            )
        }
        // Act
        composeTree.onNodeWithTag(SignUpButtonTag).performClick()
        // Assert
        composeTree.onNodeWithTag(SignUpButtonTag).assertExists().assertIsDisplayed()
        assertTrue(onSignUpRequest)
    }
}