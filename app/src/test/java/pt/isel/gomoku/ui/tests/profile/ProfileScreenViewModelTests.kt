package pt.isel.gomoku.ui.tests.profile

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.repository.interfaces.TokenRepository
import pt.isel.gomoku.ui.screens.profile.ProfileScreenViewModel
import pt.isel.gomoku.ui.services.userService
import pt.isel.gomoku.utils.MockMainDispatcherRule

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileScreenViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())



    private val tokenRepo = mockk<TokenRepository> {
        coEvery { updateOrRemoveLocalToken(null) } returns Unit
    }

    @Test
    fun initially_both_name_and_avatar_value_are_default() = runTest {
        // Arrange
        val sut = ProfileScreenViewModel(userService, tokenRepo)
        // Act
        assertNull(sut.avatar)
        assertTrue(sut.name.isEmpty())
    }
}