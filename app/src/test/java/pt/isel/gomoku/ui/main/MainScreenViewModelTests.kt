package pt.isel.gomoku.ui.main

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
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.domain.IOState
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.Loaded
import pt.isel.gomoku.domain.Loading
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.http.service.interfaces.UserService
import pt.isel.gomoku.repository.interfaces.TokenRepository
import pt.isel.gomoku.ui.screens.menu.MainScreenViewModel
import pt.isel.gomoku.utils.MockMainDispatcherRule
import pt.isel.gomoku.utils.SuspendingGate

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTests {
    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    private val authUser = UserDetails(
        id = 1,
        name = "Diogo",
        email = "diogo@gmail.com",
        avatarUrl = null,
        role = "user",
        createdAt = "2021-04-01T00:00:00.000Z",
    )

    private val userService = mockk<UserService> {
        coEvery { getAuthenticatedUser() } returns authUser
    }

    private val tokenRepo = mockk<TokenRepository> {
        coEvery { getLocalToken() } returns "token"
    }

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

        // Lets wait for the flow to emit the first value
        withTimeout(1000) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        assertTrue("Expected Idle bot got $collectedState instead", collectedState is Idle)
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

        // Lets wait for the flow to emit the first value
        withTimeout(1000) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        assertTrue(
            "Expected Loaded bot got $lastCollectedState instead",
            lastCollectedState is Loaded
        )
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

        // Lets wait for the flow to emit the first value
        withTimeout(1000) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        assertTrue(
            "Expected Loading bot got $secondCollectedState instead",
            secondCollectedState is Loading
        )
        assertTrue(
            "Expected Loaded bot got $lastCollectedState instead",
            lastCollectedState is Loaded
        )
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

        // Lets wait for the flow to emit the first value
        withTimeout(1000) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        assertTrue(
            "Expected Loading bot got $secondCollectedState instead",
            secondCollectedState is Loading
        )
        assertTrue(
            "Expected Loaded bot got $lastCollectedState instead",
            lastCollectedState is Loaded
        )
    }
}