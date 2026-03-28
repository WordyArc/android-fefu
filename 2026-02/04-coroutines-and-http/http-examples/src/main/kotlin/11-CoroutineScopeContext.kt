package _11_

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

private fun printContext(
    label: String,
    context: CoroutineContext,
    scopeJob: Job
) {
    val job = context[Job]
    val name = context[CoroutineName]?.name
    val dispatcher = context[ContinuationInterceptor]

    println("=== $label ===")
    println("thread = ${Thread.currentThread().name}")
    println("name = $name")
    println("job = $job")
    println("dispatcher = $dispatcher")
    println("same job as scope = ${job === scopeJob}")
    println()
}

fun main(): Unit = runBlocking {
    val scope = CoroutineScope(
        Job() +
                Dispatchers.Default +
                CoroutineName("lesson-scope")
    )

    val scopeJob = scope.coroutineContext[Job]!!

    printContext(
        label = "scope context",
        context = scope.coroutineContext,
        scopeJob = scopeJob
    )

    val firstChild = scope.launch {
        printContext(
            label = "child #1 inherited context",
            context = coroutineContext,
            scopeJob = scopeJob
        )

        delay(300)
        println("child #1 finished on ${Thread.currentThread().name}")
        println()
    }

    val secondChild = scope.launch(
        CoroutineName("renamed-child")
    ) {
        printContext(
            label = "child #2 name overridden",
            context = coroutineContext,
            scopeJob = scopeJob
        )

        delay(300)
        println("child #2 finished on ${Thread.currentThread().name}")
        println()
    }

    val thirdChild = scope.launch(
        CoroutineName("io-child") + Dispatchers.IO
    ) {
        printContext(
            label = "child #3 name and dispatcher overridden",
            context = coroutineContext,
            scopeJob = scopeJob
        )

        delay(300)
        println("child #3 finished on ${Thread.currentThread().name}")
        println()
    }

    joinAll(firstChild, secondChild, thirdChild)

    println("cancel scope")
    scope.cancel()
}