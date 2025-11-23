package _03_Functions

fun highOrderFunction(foo: () -> Unit): () -> Unit {
    return { foo() }
}

fun main() {
    fun anotherFoo() = println("Hello")

    highOrderFunction({ anotherFoo() })
}