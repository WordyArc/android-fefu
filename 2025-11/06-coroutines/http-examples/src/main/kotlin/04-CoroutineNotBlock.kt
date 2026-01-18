import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.Continuation
import kotlin.coroutines.startCoroutine

fun main(): Unit = runBlocking {
    println("1 start with thread: ${Thread.currentThread().name}")

    launch {
        delay(500)
        println("2 after delay thread: ${Thread.currentThread().name}")
    }

    println("3 fun continues with thread: ${Thread.currentThread().name}")

    delay(1000)
    println("4 done")
}
