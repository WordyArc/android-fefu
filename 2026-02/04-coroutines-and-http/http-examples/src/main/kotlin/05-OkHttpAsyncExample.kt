package _05_

import RANDOM_ANIME_URL
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.CountDownLatch

fun main() {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(RANDOM_ANIME_URL)
        .get()
        .header("Accept", "application/json")
        .build()

    val latch = CountDownLatch(1)

    println("main thread before enqueue: ${Thread.currentThread().name}")

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("failure thread: ${Thread.currentThread().name}")
            println("network error: ${e.message}")
            latch.countDown()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                println("response thread: ${Thread.currentThread().name}")
                println("HTTP ${it.code}")
                println(it.body?.string()?.take(500).orEmpty())
            }
            latch.countDown()
        }
    })

    println("main thread after enqueue: ${Thread.currentThread().name}")
    println("main thread is not blocked")

    latch.await()
}