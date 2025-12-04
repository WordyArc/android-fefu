package _00_TrailingLambda

fun applyTo10(op: (Int) -> Int): Int = op(10)

fun main() {
    val plusOne: (Int) -> Int = { x: Int -> x + 1 }
    val square: (Int) -> Int = { x: Int -> x * x }

    println(applyTo10(plusOne))
    println(applyTo10(square))

//    println(applyTo10 {  })
}