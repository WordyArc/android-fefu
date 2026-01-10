fun fetchNumberAsync(callback: (Int) -> Unit) {
    Thread {
        Thread.sleep(500)
        callback(42)
    }.start()
}

fun main() {
    println("Start (thread=${Thread.currentThread().name})")

    fetchNumberAsync { value ->
        println("Callback got value=$value (thread=${Thread.currentThread().name})")
    }

    println("After calling fetchNumberAsync (thread=${Thread.currentThread().name})")

    Thread.sleep(1000)
}
