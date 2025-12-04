package _02_Generics

class Animal

fun <T> swap(pair: Pair<T, T>): Pair<T, T> =
    Pair(pair.second, pair.first)

fun main() {
    println(swap(Pair(1, 2)))
    println(swap(Pair("a", "b")))
    swap(Pair(Animal(), Animal()))
}
