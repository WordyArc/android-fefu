package _01_

import java.net.HttpURLConnection
import java.net.URL

private const val BASE = "https://api.jikan.moe/v4"

fun httpGet(url: String): Pair<Int, String> {
    val conn = (URL(url).openConnection() as HttpURLConnection).apply {
        requestMethod = "GET"
        connectTimeout = 5_000
        readTimeout = 10_000
        setRequestProperty("Accept", "application/json")
    }

    val code = conn.responseCode
    val stream = if (code in 200..299) conn.inputStream else conn.errorStream
    val body = stream.bufferedReader().use { it.readText() }
    conn.disconnect()
    return code to body
}

fun main() {
    val url = "$BASE/random/anime"
    val (code, body) = httpGet(url)

    println("GET $url")
    println("HTTP $code")
    println(body.take(400))
}
