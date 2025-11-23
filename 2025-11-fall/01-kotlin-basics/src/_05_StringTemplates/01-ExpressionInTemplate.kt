package _05_StringTemplates

fun main() {
    val condition = true
    println(
        "${if (condition) 'a' else 'b'}"
    )
    val x = 11
    println("$x + 4 = ${x + 4}")
}