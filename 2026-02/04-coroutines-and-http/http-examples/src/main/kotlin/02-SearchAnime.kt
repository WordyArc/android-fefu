package _02_

import BASE_URL
import readBody
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun main() {
    val query = URLEncoder.encode("Frieren", StandardCharsets.UTF_8)
    val limit = 3
    val url = "$BASE_URL/anime?q=$query&limit=$limit"

    val conn = (URL(url).openConnection() as HttpURLConnection).apply {
        requestMethod = "GET"
        connectTimeout = 5_000
        readTimeout = 10_000
        setRequestProperty("Accept", "application/json")
    }

    try {
        val code = conn.responseCode
        val body = conn.readBody()

        println("GET $url")
        println("HTTP $code")
        println(body.take(600))
    } finally {
        conn.disconnect()
    }
}