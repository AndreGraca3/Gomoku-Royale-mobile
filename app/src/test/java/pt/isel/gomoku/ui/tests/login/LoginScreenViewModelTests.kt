package pt.isel.gomoku.ui.tests.login

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.ui.screens.login.LoginScreenViewModel
import pt.isel.gomoku.ui.services.userService
import pt.isel.gomoku.utils.MockMainDispatcherRule
import pt.isel.gomoku.utils.SuspendingGate
import pt.isel.gomoku.utils.awaitAndThenAssert

@OptIn(ExperimentalCoroutinesApi::class)
class LoginScreenViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    @Test
    fun initially_the_loginPhaseFlow_is_idle() = runTest {
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

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            Assert.assertTrue(
                "Expected Idle but got $collectedState instead",
                collectedState is Idle
            )
        }
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
                if (it is Loading) {
                    collectedState = it
                    gate.open()
                }
            }
        }
        sut.email = "dummy@gmail.com"
        sut.password = "dummyPassword"
        sut.createToken()

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            Assert.assertTrue(
                "Expected Loading but got $collectedState instead",
                collectedState is Loading
            )
        }
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
        sut.email = "dummy@gmail.com"
        sut.password = "dummyPassword"
        sut.createToken()

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            Assert.assertTrue(
                "Expected Loaded but got $collectedState instead",
                collectedState is Loaded
            )
        }
    }
}