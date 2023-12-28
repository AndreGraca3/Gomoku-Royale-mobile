package pt.isel.gomoku.http.gomokuroyale

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
import pt.isel.gomoku.domain.user.User
import pt.isel.gomoku.http.MockWebServerRule
import pt.isel.gomoku.http.ServerProblemType
import pt.isel.gomoku.http.UserProblemType
import pt.isel.gomoku.http.model.Siren
import pt.isel.gomoku.http.model.UserCreationInputModel
import pt.isel.gomoku.http.model.UserIdOutputModel
import pt.isel.gomoku.http.service.gomokuroyale.UserServiceImpl
import pt.isel.gomoku.http.service.result.APIException

@OptIn(ExperimentalCoroutinesApi::class)
class UserServiceImplTests {

    @get:Rule
    val rule = MockWebServerRule()

    private val userInput = UserCreationInputModel(
        name = "Diogo",
        email = "Diogo@gmail.com",
        password = "Diogo123",
        avatarUrl = null
    )

    @Test
    fun `createUser returns user's identifier produced by the API`() {
        // Arrange
        val expected = UserIdOutputModel(1)

        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(201)
                .addHeader("Content-type", "application/vnd.siren+json")
                .setBody(rule.gson.toJson(Siren(properties = expected)))
        )

        val userService = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            usersRequestUrl = rule.webServer.url("/").toUrl(),
        )

        // Act
        val actual = runBlocking { userService.createUser(userInput) }

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `createUser throws APIException with user creation Problem`() {
        // Arrange
        val sut = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            usersRequestUrl = rule.webServer.url("/").toUrl(),
        )

        // Act & Assert
        val ex = assertThrows(APIException::class.java) {
            runBlocking {
                sut.createUser(userInput)
            }
        }
        assertEquals(401, ex.problem.status)
        assertEquals(UserProblemType.INVALID_EMAIL, ex.problem.type)
    }

    @Test
    fun `API throws APIException when API internal server error`() {
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
        val ex = assertThrows(APIException::class.java) {
            runBlocking {
                sut.createUser(userInput)
            }
        }

        assertEquals(500, ex.problem.status)
        assertEquals(ServerProblemType.INTERNAL_SERVER_ERROR, ex.problem.type)
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
                sut.createUser(userInput)
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
                .setBody(rule.gson.toJson(Siren(properties = expected)))
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