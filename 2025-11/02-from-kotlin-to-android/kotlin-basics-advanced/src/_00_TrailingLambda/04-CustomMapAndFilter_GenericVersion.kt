package _00_TrailingLambda

fun <T, R> Iterable<T>.myMap(transform: (T) -> R): List<R> {
    val result = ArrayList<R>()
    for (element in this) result.add(transform(element))
    return result
}

fun <T> Iterable<T>.myFilter(predicate: (T) -> Boolean): List<T> {
    val result = ArrayList<T>()
    for (item in this) if (predicate(item)) result.add(item)
    return result
}

fun main() {
    val nums = listOf(1, 2, 3, 4, 5)

    val squares = nums.myMap { it * it }
    val evens = nums.myFilter { it % 2 == 0 }

    println(squares)
    println(evens)
}
