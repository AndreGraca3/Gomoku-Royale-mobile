package pt.isel.gomoku.http.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import pt.isel.gomoku.http.model.Problem
import pt.isel.gomoku.http.model.Siren
import pt.isel.gomoku.http.service.result.APIException
import java.io.IOException
import java.net.URL
import kotlin.coroutines.resumeWithException

abstract class GomokuService {

    companion object {
        const val GOMOKU_API_URL = "https://gomoku.serveo.net/api"
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
                    if (!response.isSuccessful) {
                        if (response.code >= 500) {
                            it.resumeWithException(APIException(Problem.INTERNAL_SERVER_ERROR))
                            return
                        }
                        val problem = gson.fromJson(body?.string(), Problem::class.java)
                        Log.v("requestHandler", "Result of call to API: $problem")
                        it.resumeWithException(APIException(problem))
                    } else {
                        val type = object : TypeToken<Siren<T>>() {}.type
                        val res = gson.fromJson<Siren<T>>(
                            body?.string(),
                            type
                        ).properties
                        it.resumeWith(Result.success(res))
                    }
                }
            })
            it.invokeOnCancellation { call.cancel() }
        }

    protected fun Request.Builder.buildRequest(
        url: URL,
        method: HttpMethod,
        input: Any = HttpMethod.GET
    ): Request {
        val hypermediaType = "application/json".toMediaType()
        val request = this
            .url(url)
            .addHeader("accept", hypermediaType.toString())

        when (method) {
            HttpMethod.GET -> request.get()
            HttpMethod.POST -> request.post(gson.toJson(input).toRequestBody(hypermediaType))
            HttpMethod.PUT -> request.put(gson.toJson(input).toRequestBody(hypermediaType))
            HttpMethod.PATCH -> request.patch(gson.toJson(input).toRequestBody(hypermediaType))
            HttpMethod.DELETE -> request.delete()
        }

        return request.build()
    }

    protected enum class HttpMethod {
        GET,
        POST,
        PUT,
        PATCH,
        DELETE,
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