package pt.isel.gomoku.ui.login

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.ui.screens.login.LoginScreenViewModel
import pt.isel.gomoku.utils.MockMainDispatcherRule
import pt.isel.gomoku.utils.SuspendingGate

@OptIn(ExperimentalCoroutinesApi::class)
class LoginScreenViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    private val userService = mockk<UserService> {
        coEvery { createToken(any()) } returns Unit
    }

    @Test
    fun createToken_is_initially_idle() = runTest {
        // Arrange
        val sut = LoginScreenViewModel(userService)
        // Act
        val gate = SuspendingGate()
        var collectedState: IOState<Unit>? = null
        val collectJob = launch {
            sut.loginPhase.collect {
                collectedState = it
                gate.open()
            }
        }

        // Lets wait for the flow to emit the first value
        withTimeout(1000) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        Assert.assertTrue("Expected Idle bot got $collectedState instead", collectedState is Idle)
    }

    @Test
    fun createToken_is_loading_after_createToken() = runTest {
        // Arrange
        val sut = LoginScreenViewModel(userService)
        // Act
        val gate = SuspendingGate()
        var collectedState: IOState<Unit>? = null
        val collectJob = launch {
            sut.loginPhase.collect {
                println("collected $it")
                if(it is Loading) {
                    collectedState = it
                    gate.open()
                }
            }
        }
        sut.createToken()

        // Lets wait for the flow to emit the first value
        withTimeout(1000) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        Assert.assertTrue(
            "Expected Loading bot got $collectedState instead",
            collectedState is Loading
        )
    }

    @Test
    fun createToken_is_loaded_after_createToken() = runTest {
        // Arrange
        val sut = LoginScreenViewModel(userService)
        // Act
        val gate = SuspendingGate()
        var collectedState: IOState<Unit>? = null
        val collectJob = launch {
            sut.loginPhase.collectLatest {
                println("collected $it")
                if (it is Loaded) {
                    collectedState = it
                    gate.open()
                }
            }
        }
        sut.createToken()

        // Lets wait for the flow to emit the first value
        withTimeout(1000) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        Assert.assertTrue(
            "Expected Loaded bot got $collectedState instead",
            collectedState is Loaded
        )
    }
}