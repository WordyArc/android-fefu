package _02_Types

fun printSomething(): Unit {
    println("Test Unit")
}

fun fooNothing(): Nothing = throw Exception("Test Unit")

fun returnAny(arg: Any): Any = arg
