package pt.isel.gomoku.signup

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.ui.screens.login.LoginButtonTag
import pt.isel.gomoku.ui.screens.login.LoginScreenTag
import pt.isel.gomoku.ui.screens.signup.SignUpActivity
import pt.isel.gomoku.ui.screens.signup.SignUpEmailInputTag
import pt.isel.gomoku.ui.screens.signup.SignUpNameInputTag
import pt.isel.gomoku.ui.screens.signup.SignUpPasswordInputTag
import pt.isel.gomoku.utils.createPreserveDefaultDependenciesComposeRuleNoActivity

class SignupActivityTests {

    @get:Rule
    val testRule = createPreserveDefaultDependenciesComposeRuleNoActivity()

    @Test
    fun pressing_signup_navigates_to_login() {
        // Arrange
        val name = "Diogo"
        val email = "Diogo@gmail.com"
        val password = "123"
        ActivityScenario.launch(SignUpActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(SignUpNameInputTag).performTextInput(name)
            testRule.onNodeWithTag(SignUpEmailInputTag).performTextInput(email)
            testRule.onNodeWithTag(SignUpPasswordInputTag).performTextInput(password)
            testRule.onNodeWithTag(LoginButtonTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(LoginScreenTag).assertExists()
        }
    }

    @Test
    fun input_fields_content_is_preserved_on_reconfiguration() {
        // Arrange
        val name = "Diogo"
        val email = "Diogo@gmail.com"
        val password = "123"
        ActivityScenario.launch(SignUpActivity::class.java).use { scenario ->
            // Still Arranging
            testRule.onNodeWithTag(SignUpNameInputTag).performTextInput(name)
            testRule.onNodeWithTag(SignUpEmailInputTag).performTextInput(email)
            testRule.onNodeWithTag(SignUpPasswordInputTag).performTextInput(password)
            // Act
            scenario.onActivity { it.recreate() }
            // Assert
            testRule.onNodeWithTag(SignUpNameInputTag).assertTextContains(name)
            testRule.onNodeWithTag(SignUpEmailInputTag).assertTextContains(email)
            testRule.onNodeWithTag(SignUpPasswordInputTag).assertTextContains(password)
        }
    }
}