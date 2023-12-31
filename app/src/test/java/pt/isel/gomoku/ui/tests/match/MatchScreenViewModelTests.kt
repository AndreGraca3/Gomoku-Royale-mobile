package pt.isel.gomoku.ui.tests.match

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.domain.game.match.Match
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.ui.screens.match.MatchScreenViewModel
import pt.isel.gomoku.ui.services.match
import pt.isel.gomoku.ui.services.matchService
import pt.isel.gomoku.ui.services.statsService
import pt.isel.gomoku.ui.services.userService
import pt.isel.gomoku.utils.MockMainDispatcherRule
import pt.isel.gomoku.utils.SuspendingGate
import pt.isel.gomoku.utils.awaitAndThenAssert
import pt.isel.gomoku.utils.xAssertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class MatchScreenViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    @Test
    fun initially_the_matchFlow_is_idle_and_users_are_with_default_value() = runTest {
        // Arrange
        val sut = MatchScreenViewModel(userService, statsService, matchService)
        // Act
        val gate = SuspendingGate()
        var collectedState: IOState<Match?>? = null
        val collectJob = launch {
            sut.match.collect {
                collectedState = it
                gate.open()
            }
        }

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val state = collectedState
            xAssertNotNull(state)
            Assert.assertTrue("Expected Idle but got $state instead", state is Loading)
        }
        assertEquals(null, sut.opponentUser)
        assertEquals(null, sut.currentUser)
    }

    @Test
    fun when_the_matchFlow_is_collected_the_first_value_is_emitted() = runTest {
        // Arrange
        val sut = MatchScreenViewModel(userService, statsService, matchService)
        // Act
        val gate = SuspendingGate()
        var lastCollectedState: IOState<Match?>? = null
        val collectJob = launch {
            sut.match.collectLatest {
                if (it is Loaded) {
                    lastCollectedState = it
                    gate.open()
                }
            }
        }
        sut.getMatch(match.id)

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val state = lastCollectedState
            xAssertNotNull(state)
            TestCase.assertTrue("Expected Idle but got $state instead", state is Loaded)
            TestCase.assertEquals(match, state.getOrNull())
        }
    }

    @Test
    fun the_matchFlow_passes_through_loading_before_its_loaded() = runTest {
        // Arrange
        val sut = MatchScreenViewModel(userService, statsService, matchService)
        // Act
        val gate = SuspendingGate()
        var secondCollectedState: IOState<Match?>? = null
        var lastCollectedState: IOState<Match?>? = null
        val collectJob = launch {
            sut.match.collect {
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
        sut.getMatch(match.id)

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
            assertEquals(
                match,
                lastState.getOrNull()
            )
        }
    }

    @Test
    fun getMatch_does_not_change_if_state_is_already_not_idle() = runTest {
        // Arrange
        val sut = MatchScreenViewModel(userService, statsService, matchService)
        // Act
        val gate = SuspendingGate()
        var secondCollectedState: IOState<Match?>? = null
        var lastCollectedState: IOState<Match?>? = null
        val collectJob = launch {
            sut.match.collect {
                when (it) {
                    is Loading -> {
                        if (secondCollectedState != null) {
                            Assert.fail("fetchAuthenticatedUser changed state twice to Loading")
                        } else {
                            secondCollectedState = it
                            sut.getMatch(match.id)
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
        sut.getMatch(match.id)

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
            assertEquals(
                match,
                lastState?.getOrNull()
            )
        }
    }
}