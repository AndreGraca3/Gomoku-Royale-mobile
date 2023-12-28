package pt.isel.gomoku.http

// status codes
object StatusCode {
    const val OK = 200
    const val CREATED = 201
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val NOT_FOUND = 404
    const val CONFLICT = 409
    const val INTERNAL_SERVER_ERROR = 500
}

// media types
object MediaType {
    const val SIREN = "application/vnd.siren+json"
    const val PROBLEM = "application/problem+json"
}

const val PROBLEM_BASE_TYPE = "https://gomokuroyale.pt/probs/"

// user problem types
object UserProblemType {
    const val USER_ALREADY_EXISTS = "${PROBLEM_BASE_TYPE}user-already-exists"
    const val INVALID_EMAIL = "${PROBLEM_BASE_TYPE}invalid-email"
    const val INSECURE_PASSWORD = "${PROBLEM_BASE_TYPE}insecure-password"
    const val INVALID_CREDENTIALS = "${PROBLEM_BASE_TYPE}invalid-credentials"
}

// server problem types
object ServerProblemType {
    const val INTERNAL_SERVER_ERROR = "${PROBLEM_BASE_TYPE}internal-server-error"
}

// match problem types
object MatchProblemType {
}