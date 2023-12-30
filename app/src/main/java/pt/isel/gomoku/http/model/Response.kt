package pt.isel.gomoku.http.model

sealed class Response<out T>

data class Siren<T>(
    val properties: T,
) : Response<T>()

data class Problem(
    val type: String,
    val title: String,
    val status: Int,
    val detail: String,
) : Response<Nothing>() {

    companion object {
        val INTERNAL_SERVER_ERROR = Problem(
            type = "https://gomokuroyale.pt/probs/internal-server-error",
            title = "Something went wrong",
            status = 500,
            detail = "Oh no! An unexpected error occurred"
        )
    }

    override fun toString(): String {
        return "{type=$type, title=$title, status=$status, detail=$detail}"
    }
}