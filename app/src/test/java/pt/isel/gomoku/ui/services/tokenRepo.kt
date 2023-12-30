package pt.isel.gomoku.ui.services

import io.mockk.coEvery
import io.mockk.mockk
import pt.isel.gomoku.repository.interfaces.TokenRepository

/** Token repo **/
val tokenRepo = mockk<TokenRepository> {
    coEvery { getLocalToken() } returns "token"
}