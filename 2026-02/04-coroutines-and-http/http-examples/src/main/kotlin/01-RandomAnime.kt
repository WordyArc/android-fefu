package _01_

import RANDOM_ANIME_URL
import readBody
import java.net.HttpURLConnection
import java.net.URL

fun main() {
    val conn = (URL(RANDOM_ANIME_URL).openConnection() as HttpURLConnection).apply {
        requestMethod = "GET"
        connectTimeout = 5_000
        readTimeout = 10_000
        setRequestProperty("Accept", "application/json")
    }

    try {
        val code = conn.responseCode
        val body = conn.readBody()

        println("GET $RANDOM_ANIME_URL")
        println("HTTP $code")
        println(body.take(400))
    } finally {
        conn.disconnect()
    }
}