package _12_

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val scope = CoroutineScope(
        Job() +
                Dispatchers.Default +
                CoroutineName("screen-scope")
    )

    scope.launch(CoroutineName("load-anime")) {
        try {
            repeat(10) { step ->
                delay(300)
                println("load-anime: step $step, thread=${Thread.currentThread().name}")
            }
        } finally {
            println("load-anime cancelled")
        }
    }

    scope.launch(CoroutineName("load-manga")) {
        try {
            repeat(10) { step ->
                delay(300)
                println("load-manga: step $step, thread=${Thread.currentThread().name}")
            }
        } finally {
            println("load-manga cancelled")
        }
    }

    delay(1100)

    println("cancel whole scope")
    scope.cancel()

    delay(500)
    println("done")
}