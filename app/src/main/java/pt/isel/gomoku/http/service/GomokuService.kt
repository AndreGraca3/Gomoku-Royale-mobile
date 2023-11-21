package pt.isel.gomoku.http.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import pt.isel.gomoku.http.model.siren.SirenEntity
import java.io.IOException
import java.lang.reflect.Type
import java.net.URL
import kotlin.coroutines.resumeWithException

abstract class GomokuService {

    companion object {
        const val GOMOKU_API_URL = "http://192.168.1.2:8080"
    }

    abstract val client: OkHttpClient
    abstract val gson: Gson

    protected suspend inline fun <reified T> requestHandler(request: Request): T =
        suspendCancellableCoroutine {
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        it.resumeWithException(Exception(response.message))
                    } else {
                        val type = object : TypeToken<SirenEntity<T>>() {}.type
                        val res = gson.fromJson<SirenEntity<T>>(
                            body.string(),
                            type
                        ).properties
                        it.resumeWith(Result.success(res))
                    }
                }
            })
            it.invokeOnCancellation { call.cancel() }
        }
}

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