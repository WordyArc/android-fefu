package _03_Functions

fun someFoo(isActive: Boolean, value: String) {
    if (isActive) {
        println("Active")
    } else {
        println(value)
    }
}

fun main(args: Array<String>) {
    someFoo(true, "Hello")
    someFoo(isActive = true, value = "Hello")
    someFoo(value = "Hello", isActive = true)
}