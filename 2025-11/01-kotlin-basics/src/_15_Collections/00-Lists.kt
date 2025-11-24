package _15_Collections

fun main() {
    val ints = listOf(99, 3, 5, 7, 11, 13)
    println(ints)

    var result = ""
    for (i in ints) {
        result += "$i "
    }
    println(result)

    println(ints[4])
}