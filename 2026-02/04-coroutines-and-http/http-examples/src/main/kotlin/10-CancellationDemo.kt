package _10_

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val job = launch {
        try {
            repeat(10) { step ->
                delay(300)
                println("working... step $step")
            }
        } finally {
            println("coroutine was cancelled or finished")
        }
    }

    delay(1100)
    println("cancel job")
    job.cancel()
    job.join()

    println("main finished")
}