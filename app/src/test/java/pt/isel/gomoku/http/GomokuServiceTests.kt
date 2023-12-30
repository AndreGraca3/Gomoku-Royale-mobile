package pt.isel.gomoku.http

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import pt.isel.gomoku.domain.stats.Rank
import pt.isel.gomoku.http.model.Problem
import pt.isel.gomoku.http.model.Siren
import pt.isel.gomoku.http.model.UserCreationInputModel
import pt.isel.gomoku.http.model.UserIdOutputModel
import pt.isel.gomoku.http.model.UserInfo
import pt.isel.gomoku.http.service.gomokuroyale.UserServiceImpl
import pt.isel.gomoku.http.service.result.APIException

@OptIn(ExperimentalCoroutinesApi::class)
class GomokuServiceTests {

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
        val sut: UserServiceImpl = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            usersRequestUrl = rule.webServer.url("/").toUrl()
        )
        val expected = UserIdOutputModel(1)

        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(StatusCode.CREATED)
                .addHeader("Content-type", MediaType.SIREN)
                .setBody(rule.gson.toJson(Siren(properties = expected)))
        )

        // Act
        val actual = runBlocking { sut.createUser(userInput) }

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `createUser throws APIException with user creation Problem`() {
        // Arrange
        val sut: UserServiceImpl = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            usersRequestUrl = rule.webServer.url("/").toUrl()
        )
        val problem = Problem(
            type = UserProblemType.INVALID_EMAIL,
            title = "Invalid email",
            detail = "The email is not valid",
            status = StatusCode.BAD_REQUEST
        )
        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(StatusCode.BAD_REQUEST)
                .addHeader("Content-type", MediaType.PROBLEM)
                .setBody(rule.gson.toJson(problem))
        )

        // Act & Assert
        val ex = assertThrows(APIException::class.java) {
            runBlocking {
                sut.createUser(userInput)
            }
        }
        assertEquals(StatusCode.BAD_REQUEST, ex.problem.status)
        assertEquals(UserProblemType.INVALID_EMAIL, ex.problem.type)
    }

    @Test
    fun `API throws APIException when API internal server error`() {
        // Arrange
        val sut: UserServiceImpl = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            usersRequestUrl = rule.webServer.url("/").toUrl()
        )
        rule.webServer.enqueue(
            MockResponse().setResponseCode(500)
        )

        // Act & Assert
        val ex = assertThrows(APIException::class.java) {
            runBlocking {
                sut.createUser(userInput)
            }
        }

        assertEquals(StatusCode.INTERNAL_SERVER_ERROR, ex.problem.status)
        assertEquals(ServerProblemType.INTERNAL_SERVER_ERROR, ex.problem.type)
    }

    @Test
    fun `createUser throws CancellationException when coroutine is cancelled`() = runTest {
        // Arrange
        val sut: UserServiceImpl = UserServiceImpl(
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
        assertTrue(cancellationThrown)
    }

    @Test
    fun `getUser returns User produced by the API`() {
        // Arrange
        val sut: UserServiceImpl = UserServiceImpl(
            client = rule.httpClient,
            gson = rule.gson,
            getUserRequestUrl = { return@UserServiceImpl rule.webServer.url("/").toUrl() }
        )
        val expected = UserInfo(
            id = 1,
            name = "Diogo",
            avatarUrl = null,
            role = "user",
            rank = Rank(
                "Silver",
                "url.com"
            ),
            createdAt = "2021-05-01T00:00:00.000Z",
        )

        rule.webServer.enqueue(
            MockResponse()
                .setResponseCode(StatusCode.OK)
                .addHeader("Content-type", MediaType.SIREN)
                .setBody(rule.gson.toJson(Siren(properties = expected)))
        )

        // Act
        val actual = runBlocking { sut.getUser(2) }

        // Assert
        assertEquals(expected, actual)
    }
}