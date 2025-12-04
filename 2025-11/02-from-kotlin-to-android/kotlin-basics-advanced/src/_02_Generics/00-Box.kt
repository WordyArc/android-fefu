package _02_Generics

class IntBox(var value: Int)

class StringBox(var value: String)



















class Box<T>(var value: T)

fun main() {
    val a = Box(10)
    val b = Box("some unuseful string")

//    a.value = "oops"
    b.value = b.value.uppercase()
    println(a.value)
    println(b.value)
}