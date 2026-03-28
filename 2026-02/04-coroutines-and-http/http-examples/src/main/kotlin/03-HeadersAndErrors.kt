package _03_

import BASE_URL
import RANDOM_ANIME_URL
import readBody
import java.net.HttpURLConnection
import java.net.URL

private fun printResponse(url: String) {
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
        println("--- headers ---")
        conn.headerFields.forEach { (name, values) ->
            println("${name ?: "<status-line>"}: ${values.joinToString()}")
        }
        println("--- body ---")
        println(body.take(300))
        println()
    } finally {
        conn.disconnect()
    }
}

fun main() {
    println("=== SUCCESS ===")
    printResponse(RANDOM_ANIME_URL)

    println("=== ERROR ===")
    printResponse("$BASE_URL/this-endpoint-does-not-exist")
}