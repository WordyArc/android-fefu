import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

fun main() {
    val tasks = 100_000
    val poolSize = Runtime.getRuntime().availableProcessors() * 2

    val pool = Executors.newFixedThreadPool(poolSize)
    val latch = CountDownLatch(tasks)

    val start = System.nanoTime()

    repeat(tasks) {
        pool.execute {
            try {
                Thread.sleep(2)
            } finally {
                latch.countDown()
            }
        }
    }

    latch.await()
    pool.shutdown()

    val elapsedMs = (System.nanoTime() - start) / 1_000_000
    println("Done: $tasks tasks on pool=$poolSize threads in ${elapsedMs}ms")
}
