package pt.isel.gomoku.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.ui.screens.login.LoginEmailInputTag
import pt.isel.gomoku.ui.screens.login.LoginButtonTag
import pt.isel.gomoku.ui.screens.login.LoginScreen
import pt.isel.gomoku.ui.screens.login.LoginPasswordInputTag
import pt.isel.gomoku.ui.screens.login.SignUpButtonTag

class LoginScreenTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun login_screen_displays_two_input_fields() {
        // Arrange
        var onEmailChange = false
        var onPasswordChange = false
        composeTree.setContent {
            LoginScreen(
                email = "",
                password = "",
                onEmailChange = { onEmailChange = true },
                onPasswordChange = { onPasswordChange = true },
                onLoginRequest = {},
                onSignUpRequest = {}
            )
        }
        // Act
        composeTree.onNodeWithTag(LoginEmailInputTag).performTextInput("Diogo@gmail.com")
        composeTree.onNodeWithTag(LoginPasswordInputTag).performTextInput("123")
        // Assert
        composeTree.onNodeWithTag(LoginEmailInputTag).assertExists().assertIsDisplayed()
        composeTree.onNodeWithTag(LoginPasswordInputTag).assertExists().assertIsDisplayed()
        assertTrue(onEmailChange)
        assertTrue(onPasswordChange)
    }

    @Test
    fun login_screen_displays_login_button_and_pressing_calls_callback() {
        // Arrange
        var onLoginRequested = false
        composeTree.setContent {
            LoginScreen(
                email = "",
                password = "",
                onEmailChange = {},
                onPasswordChange = {},
                onLoginRequest = { onLoginRequested = true },
                onSignUpRequest = {}
            )
        }
        // Act
        composeTree.onNodeWithTag(LoginButtonTag).performClick()
        // Assert
        composeTree.onNodeWithTag(LoginButtonTag).assertExists().assertIsDisplayed()
        assertTrue(onLoginRequested)
    }

    @Test
    fun login_screen_displays_signup_button_and_pressing_calls_callback() {
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