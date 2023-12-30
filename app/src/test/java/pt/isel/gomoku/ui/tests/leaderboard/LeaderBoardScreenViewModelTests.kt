package pt.isel.gomoku.ui.tests.leaderboard

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
import pt.isel.gomoku.http.model.LeaderBoard
import pt.isel.gomoku.ui.screens.leaderboard.LeaderBoardScreenViewModel
import pt.isel.gomoku.ui.services.leaderBoard
import pt.isel.gomoku.ui.services.leaderBoardService
import pt.isel.gomoku.utils.MockMainDispatcherRule
import pt.isel.gomoku.utils.SuspendingGate
import pt.isel.gomoku.utils.awaitAndThenAssert
import pt.isel.gomoku.utils.xAssertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class LeaderBoardScreenViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    @Test
    fun initially_the_topPlayersFlow_is_idle() = runTest {
        // Arrange
        val sut = LeaderBoardScreenViewModel(leaderBoardService)
        // Act
        val gate = SuspendingGate()
        var collectedState: IOState<LeaderBoard?>? = null
        val collectJob = launch {
            sut.topPlayers.collect {
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
    fun when_the_topPlayersFlow_is_collected_the_first_value_is_emitted() = runTest {
        // Arrange
        val sut = LeaderBoardScreenViewModel(leaderBoardService)
        // Act
        val gate = SuspendingGate()
        var lastCollectedState: IOState<LeaderBoard?>? = null
        val collectJob = launch {
            sut.topPlayers.collectLatest {
                if (it is Loaded) {
                    lastCollectedState = it
                    gate.open()
                }
            }
        }
        sut.loadTopPlayers()

        // Assert
        gate.awaitAndThenAssert(1000) {
            collectJob.cancelAndJoin()
            val state = lastCollectedState
            xAssertNotNull(state)
            assertTrue("Expected Idle but got $state instead", state is Loaded)
            assertEquals(leaderBoard, state.getOrNull())
        }
    }

    @Test
    fun the_topPlayersFlow_passes_through_loading_before_its_loaded() = runTest {
        // Arrange
        val sut = LeaderBoardScreenViewModel(leaderBoardService)
        // Act
        val gate = SuspendingGate()
        var secondCollectedState: IOState<LeaderBoard?>? = null
        var lastCollectedState: IOState<LeaderBoard?>? = null
        val collectJob = launch {
            sut.topPlayers.collect {
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
        sut.loadTopPlayers()

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
                leaderBoard,
                lastState.getOrNull()
            )
        }
    }

    @Test
    fun loadTopPlayers_does_not_change_if_state_is_already_not_idle() = runTest {
        // Arrange
        val sut = LeaderBoardScreenViewModel(leaderBoardService)
        // Act
        val gate = SuspendingGate()
        var secondCollectedState: IOState<LeaderBoard?>? = null
        var lastCollectedState: IOState<LeaderBoard?>? = null
        val collectJob = launch {
            sut.topPlayers.collect {
                when (it) {
                    is Loading -> {
                        if (secondCollectedState != null) {
                            Assert.fail("fetchAuthenticatedUser changed state twice to Loading")
                        } else {
                            secondCollectedState = it
                            sut.loadTopPlayers()
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
        sut.loadTopPlayers()

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
                leaderBoard,
                lastState?.getOrNull()
            )
        }
    }
}