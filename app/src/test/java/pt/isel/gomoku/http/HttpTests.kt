package pt.isel.gomoku.http

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.domain.siren.SirenEntity
import pt.isel.gomoku.domain.user.dto.User
import pt.isel.gomoku.domain.user.dto.UserCreateInput
import pt.isel.gomoku.domain.user.dto.UserIdOutput

@OptIn(ExperimentalCoroutinesApi::class)
class HttpTests {

    @get:Rule
    val rule = MockWebServerRule()

    private val uci = UserCreateInput(
        name = "Diogo",
        email = "Diogo@gmail.com",
        password = "Diogo123",
        avatarUrl = null
    )

    @Test
    fun `createUser returns user's identifier produced by the API`() {
        // Arrange
        val expected = UserIdOutput(1)

        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(201)
                .addHeader("Content-type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(SirenEntity(properties = expected)))
        )

        val sut = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            usersRequestUrl = rule.webServer.url("/").toUrl(),
        )

        // Act
        val actual = runBlocking { sut.createUser(uci) }

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `createUser throws UserServiceException on API access timeout`() {
        // Arrange
        val sut = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            usersRequestUrl = rule.webServer.url("/").toUrl(),
        )

        // Act & Assert
        assertThrows(UserServiceException::class.java) {
            runBlocking {
                sut.createUser(uci)
            }
        }
    }

    @Test
    fun `fetchJoke throws FetchJokeException when API internal server error`() {
        // Arrange
        rule.webServer.enqueue(
            MockResponse().setResponseCode(500)
        )

        val sut = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            usersRequestUrl = rule.webServer.url("/").toUrl()
        )

        // Act & Assert
        assertThrows(UserServiceException::class.java) {
            runBlocking {
                sut.createUser(uci)
            }
        }
    }

    @Test
    fun `fetchJoke throws CancellationException when coroutine is cancelled`() = runTest {
        // Arrange
        val sut = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            usersRequestUrl = rule.webServer.url("/").toUrl()
        )
        var cancellationThrown = false

        // Act
        val job = launch(UnconfinedTestDispatcher()) {
            try {
                sut.createUser(uci)
            } catch (e: CancellationException) {
                cancellationThrown = true
                throw e
            }
        }
        job.cancel()
        job.join()

        // Assert
        Assert.assertTrue(cancellationThrown)
    }

    @Test
    fun `getUser returns User Object produced by the API`() {
        // Arrange
        val expected = User(
            name = "Diogo",
            avatar = null,
            rank = "ola"
        )

        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(SirenEntity(properties = expected)))
        )

        val sut = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            getUserRequestUrl = rule.webServer.url("/{id}").toUrl(),
        )

        // Act
        val actual = runBlocking { sut.getUser(2) }

        // Assert
        assertEquals(expected, actual)
    }
}