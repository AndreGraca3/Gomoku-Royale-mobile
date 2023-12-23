package pt.isel.gomoku.repository.infra

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import pt.isel.gomoku.repository.interfaces.TokenRepository

class TokenRepositoryImpl(private val dataStore: DataStore<Preferences>) : TokenRepository {
    private val tokenKey = stringPreferencesKey("token")

    override suspend fun getLocalToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[tokenKey]
    }

    override suspend fun updateOrRemoveLocalToken(token: String?) {
        dataStore.edit { preferences ->
            token?.let {
                preferences[tokenKey] = it
            } ?: preferences.remove(tokenKey)
        }
    }
}