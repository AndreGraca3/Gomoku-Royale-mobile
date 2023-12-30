package pt.isel.gomoku.ui.tests.main

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.ui.screens.menu.MainScreenViewModel
import pt.isel.gomoku.ui.services.authUser
import pt.isel.gomoku.ui.services.tokenRepo
import pt.isel.gomoku.ui.services.userService
import pt.isel.gomoku.utils.MockMainDispatcherRule
import pt.isel.gomoku.utils.SuspendingGate
import pt.isel.gomoku.utils.awaitAndThenAssert
import pt.isel.gomoku.utils.xAssertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTests {
    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    @Test
    fun initially_the_authUserFlow_is_idle() = runTest {
        // Arrange
        val sut = MainScreenViewModel(userService, tokenRepo)
        // Act
        val gate = SuspendingGate()
        var collectedState: IOState<UserDetails?>? = null
        val collectJob = launch {
            sut.authUser.collect {
                collectedState = it
                gate.open()
            }
        }

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val state = collectedState
            xAssertNotNull(state)
            assertTrue("Expected Idle but got $state instead", state is Idle)
        }
    }

    @Test
    fun when_the_authUserFlow_is_collected_the_first_value_is_emitted() = runTest {
        // Arrange
        val sut = MainScreenViewModel(userService, tokenRepo)
        // Act
        val gate = SuspendingGate()
        var lastCollectedState: IOState<UserDetails?>? = null
        val collectJob = launch {
            sut.authUser.collectLatest {
                if (it is Loaded) {
                    lastCollectedState = it
                    gate.open()
                }
            }
        }
        sut.fetchAuthenticatedUser()

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val state = lastCollectedState
            xAssertNotNull(state)
            assertTrue("Expected Loaded but got $state instead", state is Loaded)
            assertEquals(authUser, state.getOrNull())
        }
    }

    @Test
    fun the_authUserFlow_passes_through_loading_before_its_loaded() = runTest {
        // Arrange
        val sut = MainScreenViewModel(userService, tokenRepo)
        // Act
        val gate = SuspendingGate()
        var secondCollectedState: IOState<UserDetails?>? = null
        var lastCollectedState: IOState<UserDetails?>? = null
        val collectJob = launch {
            sut.authUser.collect {
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
        sut.fetchAuthenticatedUser()

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val secondState = secondCollectedState
            val lastState = lastCollectedState
            xAssertNotNull(secondState)
            xAssertNotNull(lastState)
            assertTrue(
                "Expected Loading but got $secondState instead",
                secondState is Loading
            )
            assertTrue(
                "Expected Loaded but got $lastState instead",
                lastState is Loaded
            )
            assertEquals(
                authUser,
                lastState.getOrNull()
            )
        }
    }

    @Test
    fun fetchAuthenticatedUser_does_not_change_if_state_is_already_not_idle() = runTest {
        // Arrange
        val sut = MainScreenViewModel(userService, tokenRepo)
        // Act
        val gate = SuspendingGate()
        var secondCollectedState: IOState<UserDetails?>? = null
        var lastCollectedState: IOState<UserDetails?>? = null
        val collectJob = launch {
            sut.authUser.collect {
                when (it) {
                    is Loading -> {
                        if (secondCollectedState != null) {
                            Assert.fail("fetchAuthenticatedUser changed state twice to Loading")
                        } else {
                            secondCollectedState = it
                            sut.fetchAuthenticatedUser()
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
        sut.fetchAuthenticatedUser()

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val secondState = secondCollectedState
            val lastState = lastCollectedState
            assertTrue(
                "Expected Loading but got $secondState instead",
                secondState is Loading
            )
            assertTrue(
                "Expected Loaded but got $lastState instead",
                lastState is Loaded
            )
            assertEquals(
                authUser,
                lastState?.getOrNull()
            )
        }
    }
}