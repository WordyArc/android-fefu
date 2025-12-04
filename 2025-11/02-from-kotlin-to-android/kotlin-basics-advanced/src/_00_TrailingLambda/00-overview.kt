package _00_TrailingLambda

fun repeatN(n: Int, action: (Int) -> Unit) {
    for (i in 0 until n) action(i)
}

fun main() {
    repeatN(3, { i -> println("A: $i") })

    // trailing lambda
    // ..

    // it
    // ..
}
