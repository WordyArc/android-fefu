package _00_TrailingLambda

fun List<Int>.myMapInt(transform: (Int) -> Int): List<Int> {
    val result = ArrayList<Int>()
    for (el in this) result.add(transform(el))
    return result
}

fun List<Int>.myFilterInt(predicate: (Int) -> Boolean): List<Int> {
    val result = ArrayList<Int>()
    for (x in this) if (predicate(x)) result.add(x)
    return result
}

fun main() {
    val nums = listOf(1, 2, 3, 4)

    val result = nums
        .myMapInt { x -> x * x }

    println(result)
}