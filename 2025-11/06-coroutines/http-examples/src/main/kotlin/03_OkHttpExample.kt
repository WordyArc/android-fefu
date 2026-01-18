package _03_

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

private const val URL = "https://api.jikan.moe/v4/random/anime"

fun main() {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(URL)
        .get()
        .header("Accept", "application/json")
        .build()

    // СИНХРОННЫЙ запрос: блокирует текущий поток до получения ответа.
    try {
        client.newCall(request).execute().use { response ->
            println("GET $URL")
            println("HTTP ${response.code}")

            val body = response.body?.string()
            if (!response.isSuccessful) {
                System.err.println("Error body: ${body?.take(500)}")
                return
            }

            println(body?.take(500))
        }
    } catch (e: IOException) {
        System.err.println("Network error: ${e.message}")
    }
}
