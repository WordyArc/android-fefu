package _03_Functions

val foo = { }





























fun highOrderFunction(foo: () -> Unit): () -> Unit {
    return { foo() }
}

fun main() {
    fun anotherFoo() = println("Hello")

    highOrderFunction({ anotherFoo() })
}