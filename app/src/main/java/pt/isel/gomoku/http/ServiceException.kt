package pt.isel.gomoku.http

import java.net.URL

open class ServiceException(message: String, cause: Throwable? = null) : Exception(message, cause)

class UserServiceException(message: String, cause: Throwable? = null) :
    ServiceException(message, cause)

fun parameterizedURL(schema: URL, vararg pathVariables: Any): URL {
    val path = schema.path.split("/")
    val mutablePathVariable = pathVariables.toMutableList()
    val parameterizedPath = path.map {
        if (it.contains(":")) {
            mutablePathVariable.removeFirst().toString()
        } else "/$it"
    }.joinToString("") { it }
    return URL(
        "${schema.protocol}://${schema.authority}$parameterizedPath"
    )
}