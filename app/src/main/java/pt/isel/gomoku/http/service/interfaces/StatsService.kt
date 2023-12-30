package pt.isel.gomoku.http.service.interfaces

import pt.isel.gomoku.http.model.UserStatsOutputModel

interface StatsService {

    suspend fun getUserStats(id: Int): UserStatsOutputModel
}