package pt.isel.gomoku.repository.interfaces

interface TokenRepository {

    /**
     * Gets the local token if it exists, null otherwise.
     */
    suspend fun getLocalToken(): String?

    /**
     * Updates the local token, or removes it if the token is null.
     */
    suspend fun updateLocalToken(token: String?)
}
