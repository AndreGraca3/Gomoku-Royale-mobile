package pt.isel.gomoku.login

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.ui.screens.login.LoginActivity
import pt.isel.gomoku.ui.screens.login.LoginButtonTag
import pt.isel.gomoku.ui.screens.login.LoginEmailInputTag
import pt.isel.gomoku.ui.screens.login.LoginPasswordInputTag
import pt.isel.gomoku.ui.screens.login.SignUpButtonTag
import pt.isel.gomoku.ui.screens.menu.MenuScreenTag
import pt.isel.gomoku.ui.screens.signup.SignUpActivity
import pt.isel.gomoku.ui.screens.signup.SignUpScreenTag
import pt.isel.gomoku.utils.createPreserveDefaultDependenciesComposeRuleNoActivity

class LoginActivityTests {

    @get:Rule
    val testRule = createPreserveDefaultDependenciesComposeRuleNoActivity()

    @Test
    fun pressing_login_navigates_to_menu() {
        // Arrange
        ActivityScenario.launch(LoginActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(LoginEmailInputTag).performTextInput("Diogo@gmail.com")
            testRule.onNodeWithTag(LoginPasswordInputTag).performTextInput("123")
            testRule.onNodeWithTag(LoginButtonTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(MenuScreenTag).assertExists()
        }
    }

    @Test
    fun pressing_signup_navigates_to_signup() {
        // Arrange
        ActivityScenario.launch(LoginActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(SignUpButtonTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(SignUpScreenTag).assertExists()
        }
    }

    @Test
    fun input_fields_content_is_preserved_on_reconfiguration() {
        // Arrange
        val email = "Diogo@gmail.com"
        val password = "123"
        ActivityScenario.launch(SignUpActivity::class.java).use { scenario ->
            // Still Arranging
            testRule.onNodeWithTag(LoginEmailInputTag).performTextInput(email)
            testRule.onNodeWithTag(LoginPasswordInputTag).performTextInput(email)
            // Act
            scenario.onActivity { it.recreate() }
            // Assert
            testRule.onNodeWithTag(LoginEmailInputTag).assertTextContains(email)
            testRule.onNodeWithTag(LoginPasswordInputTag).assertTextContains(password)
        }
    }
}