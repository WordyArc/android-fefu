package _00_TrailingLambda


fun main() {
    val listNumbers = listOf(1, 2, 45, 66, 75, 21)

    val result = listNumbers
        .map { el -> el * 2 }
        .filter { el -> el in 5..100 }

    println(result)
}
