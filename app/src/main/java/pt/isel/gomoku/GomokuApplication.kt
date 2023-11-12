package pt.isel.gomoku

import android.app.Application
import okhttp3.OkHttpClient
import com.google.gson.Gson
import java.util.concurrent.TimeUnit

interface GomokuDependencyProvider {
    /**
     * The HTTP client used to perform HTTP requests
     */
    val httpClient: OkHttpClient

    /**
     * The JSON serializer/deserializer used to convert JSON into DTOs
     */
    val gson: Gson
}

class GomokuApplication: Application(), GomokuDependencyProvider {
    /**
     * The HTTP client used to perform HTTP requests
     */
    override val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .build()

    /**
     * The JSON serializer/deserializer used to convert JSON into DTOs
     */
    override val gson: Gson = Gson()
}