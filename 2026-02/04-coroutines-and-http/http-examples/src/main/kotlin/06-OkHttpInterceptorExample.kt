package _06_

import BASE_URL
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

fun main() {
    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val start = System.nanoTime()

            println("-> ${request.method} ${request.url}")

            val response = chain.proceed(request)

            val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start)
            println("<- HTTP ${response.code} ${response.request.url} (${tookMs} ms)")

            response
        }
        .build()

    val url = "$BASE_URL/anime".toHttpUrl().newBuilder()
        .addQueryParameter("q", "Frieren")
        .addQueryParameter("limit", "3")
        .build()

    val request = Request.Builder()
        .url(url)
        .get()
        .header("Accept", "application/json")
        .build()

    try {
        client.newCall(request).execute().use { response ->
            val body = response.body?.string().orEmpty()
            println(body.take(500))
        }
    } catch (e: IOException) {
        System.err.println("network error: ${e.message}")
    }
}