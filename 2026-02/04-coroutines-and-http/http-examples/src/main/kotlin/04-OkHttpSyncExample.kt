package _04_

import RANDOM_ANIME_URL
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

fun main() {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(RANDOM_ANIME_URL)
        .get()
        .header("Accept", "application/json")
        .build()

    println("before execute thread: ${Thread.currentThread().name}")

    try {
        client.newCall(request).execute().use { response ->
            println("after execute thread: ${Thread.currentThread().name}")
            println("GET $RANDOM_ANIME_URL")
            println("HTTP ${response.code}")

            val body = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                System.err.println("error body: ${body.take(300)}")
                return
            }

            println(body.take(500))
        }
    } catch (e: IOException) {
        System.err.println("network error: ${e.message}")
    }
}