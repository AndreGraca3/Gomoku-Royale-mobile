package pt.isel.gomoku.ui.services

import io.mockk.coEvery
import io.mockk.mockk
import pt.isel.gomoku.domain.stats.Rank
import pt.isel.gomoku.http.model.UserDetails
import pt.isel.gomoku.http.model.UserIdOutputModel
import pt.isel.gomoku.http.model.UserInfo
import pt.isel.gomoku.http.service.interfaces.UserService

/** User services **/
val userIdOutput = UserIdOutputModel(
    id = 1
)

val user1 = UserInfo(
    id = userIdOutput.id,
    name = "Diogo",
    avatarUrl = null,
    role = "user",
    createdAt = "2021-04-01T00:00:00.000Z",
    rank = Rank(
        name = "Champion",
        iconUrl = "Champion_url_icon"
    )
)

val user2 = UserInfo(
    id = 2,
    name = "André",
    avatarUrl = null,
    role = "user",
    createdAt = "2021-04-01T00:00:00.000Z",
    rank = Rank(
        name = "Champion",
        iconUrl = "Champion_url_icon"
    )
)

val authUser = UserDetails(
    id = 1,
    name = "Diogo",
    email = "diogo@gmail.com",
    avatarUrl = null,
    role = "user",
    createdAt = "2021-04-01T00:00:00.000Z",
)

val userService = mockk<UserService> {
    coEvery { createUser(any()) } returns userIdOutput
    coEvery { getAuthenticatedUser() } returns authUser
    coEvery { getUser(user1.id) } returns user1
    coEvery { getUser(user2.id) } returns user2
    coEvery { updateUser(any()) } returns user1
    coEvery { deleteUser() } returns Unit
    coEvery { createToken(any()) } returns Unit
}