package _08_

import RANDOM_ANIME_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import readBody
import java.net.HttpURLConnection
import java.net.URL

fun main(): Unit = runBlocking {
    println("runBlocking thread: ${Thread.currentThread().name}")

    val body = withContext(Dispatchers.IO) {
        println("network thread: ${Thread.currentThread().name}")

        val conn = (URL(RANDOM_ANIME_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 5_000
            readTimeout = 10_000
            setRequestProperty("Accept", "application/json")
        }

        try {
            println("HTTP ${conn.responseCode}")
            conn.readBody()
        } finally {
            conn.disconnect()
        }
    }

    println("back thread: ${Thread.currentThread().name}")
    println(body.take(400))
}