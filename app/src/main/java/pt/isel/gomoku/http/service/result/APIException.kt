package pt.isel.gomoku.http.service.result

import pt.isel.gomoku.http.model.Problem

data class APIException(val problem: Problem): Throwable(problem.detail)