package _02_Types

fun main() {
    val whole: Int = 11
    val fractional: Double = 1.4
    val trueOrFalse: Boolean = true
    val words: String = "A value"
    val character: Char = 'z'
    val lines: String = """Triple quotes let 
        |you have many lines
        |in your string""".trimMargin()
    //val closure = { }
    println(whole)
    println(fractional)
    println(trueOrFalse)
    println(words)
    println(character)
    println(lines)
}