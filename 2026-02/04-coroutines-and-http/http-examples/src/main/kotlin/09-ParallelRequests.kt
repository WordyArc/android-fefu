package _09_

import RANDOM_ANIME_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import readBody
import java.net.HttpURLConnection
import java.net.URL
import kotlin.system.measureTimeMillis

private suspend fun loadRandomAnime(index: Int): String =
    withContext(Dispatchers.IO) {
        val conn = (URL(RANDOM_ANIME_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 5_000
            readTimeout = 10_000
            setRequestProperty("Accept", "application/json")
        }

        try {
            val code = conn.responseCode
            val body = conn.readBody()
            "request #$index -> HTTP $code, body: ${body.take(120)}"
        } finally {
            conn.disconnect()
        }
    }

fun main(): Unit = runBlocking {
    val sequentialTime = measureTimeMillis {
        println("=== SEQUENTIAL ===")
        for (i in 1..2) {
            println(loadRandomAnime(i))
            println()
        }
    }

    val parallelTime = measureTimeMillis {
        println("=== PARALLEL ===")
        val results = coroutineScope {
            (1..2).map { i ->
                async { loadRandomAnime(i) }
            }.awaitAll()
        }

        results.forEach {
            println(it)
            println()
        }
    }

    println("sequential time: ${sequentialTime} ms")
    println("parallel time: ${parallelTime} ms")
}