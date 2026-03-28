package _07_

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    println("1 start thread: ${Thread.currentThread().name}")

    launch {
        delay(500)
        println("2 after delay thread: ${Thread.currentThread().name}")
    }

    println("3 function continues thread: ${Thread.currentThread().name}")

    delay(1000)
    println("4 done")
}