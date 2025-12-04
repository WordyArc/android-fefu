package _02_Generics

fun <T> lastOrDefault(default: T, list: List<T>): T =
    if (list.isEmpty()) default
    else list.last()

fun main() {
    println(lastOrDefault(0, listOf(1, 2, 3)))
    println(lastOrDefault("empty", emptyList()))
}
