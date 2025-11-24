package _17_Dataclasses

class User(val name: String, val age: Int)

fun main() {
    val u1 = User("Alex", 20)
    val u2 = User("Alex", 20)

    println(u1 == u2)
    println(u1 === u2)
}
