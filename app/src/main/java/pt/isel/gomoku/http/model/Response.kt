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
    val instance: String,
) : Response<Nothing>() {
    override fun toString(): String {
        return "{type=$type, title=$title, status=$status, detail=$detail, instance:$instance}"
    }
}