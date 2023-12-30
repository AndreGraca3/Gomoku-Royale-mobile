package pt.isel.gomoku.ui.tests.signup

import android.content.ContentResolver
import io.mockk.mockk
import junit.framework.TestCase
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
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.http.model.UserIdOutputModel
import pt.isel.gomoku.ui.screens.signup.SignUpScreenViewModel
import pt.isel.gomoku.ui.services.userIdOutput
import pt.isel.gomoku.ui.services.userService
import pt.isel.gomoku.utils.MockMainDispatcherRule
import pt.isel.gomoku.utils.SuspendingGate
import pt.isel.gomoku.utils.awaitAndThenAssert
import pt.isel.gomoku.utils.xAssertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class SignupScreenViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    private val contentResolver = mockk<ContentResolver> {}

    @Test
    fun initially_the_signUpPhaseFlow_is_idle() = runTest {
        // Arrange
        val sut = SignUpScreenViewModel(userService, contentResolver)
        // Act
        val gate = SuspendingGate()
        var collectedState: IOState<UserIdOutputModel?>? = null
        val collectJob = launch {
            sut.signUpPhase.collect {
                collectedState = it
                gate.open()
            }
        }

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val state = collectedState
            xAssertNotNull(state)
            Assert.assertTrue("Expected Idle but got $state instead", state is Idle)
        }
    }

    @Test
    fun when_the_signUpPhaseFlow_is_collected_the_first_value_is_emitted() = runTest {
        // Arrange
        val sut = SignUpScreenViewModel(userService, contentResolver)
        // Act
        val gate = SuspendingGate()
        var lastCollectedState: IOState<UserIdOutputModel?>? = null
        val collectJob = launch {
            sut.signUpPhase.collectLatest {
                if (it is Loaded) {
                    lastCollectedState = it
                    gate.open()
                }
            }
        }
        sut.createUser()

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val state = lastCollectedState
            xAssertNotNull(state)
            TestCase.assertTrue("Expected Idle but got $state instead", state is Loaded)
            TestCase.assertEquals(userIdOutput, state.getOrNull())
        }
    }

    @Test
    fun the_topPlayersFlow_passes_through_loading_before_its_loaded() = runTest {
        // Arrange
        val sut = SignUpScreenViewModel(userService, contentResolver)
        // Act
        val gate = SuspendingGate()
        var secondCollectedState: IOState<UserIdOutputModel?>? = null
        var lastCollectedState: IOState<UserIdOutputModel?>? = null
        val collectJob = launch {
            sut.signUpPhase.collect {
                when (it) {
                    is Loading -> secondCollectedState = it
                    is Loaded -> {
                        lastCollectedState = it
                        gate.open()
                    }

                    else -> {}
                }
            }
        }
        sut.createUser()

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val secondState = secondCollectedState
            val lastState = lastCollectedState
            xAssertNotNull(secondState)
            xAssertNotNull(lastState)
            Assert.assertTrue(
                "Expected Loading but got $secondState instead",
                secondState is Loading
            )
            Assert.assertTrue(
                "Expected Loaded but got $lastState instead",
                lastState is Loaded
            )
            Assert.assertEquals(
                userIdOutput,
                lastState.getOrNull()
            )
        }
    }

    @Test
    fun createUser_does_not_change_if_state_is_already_not_idle() = runTest {
        // Arrange
        val sut = SignUpScreenViewModel(userService, contentResolver)
        // Act
        val gate = SuspendingGate()
        var secondCollectedState: IOState<UserIdOutputModel?>? = null
        var lastCollectedState: IOState<UserIdOutputModel?>? = null
        val collectJob = launch {
            sut.signUpPhase.collect {
                when (it) {
                    is Loading -> {
                        if (secondCollectedState != null) {
                            Assert.fail("fetchAuthenticatedUser changed state twice to Loading")
                        } else {
                            secondCollectedState = it
                            sut.createUser()
                        }
                    }

                    is Loaded -> {
                        lastCollectedState = it
                        gate.open()
                    }

                    else -> {}
                }
            }
        }
        sut.createUser()

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val secondState = secondCollectedState
            val lastState = lastCollectedState
            Assert.assertTrue(
                "Expected Loading but got $secondState instead",
                secondState is Loading
            )
            Assert.assertTrue(
                "Expected Loaded but got $lastState instead",
                lastState is Loaded
            )
            Assert.assertEquals(
                userIdOutput,
                lastState?.getOrNull()
            )
        }
    }
}