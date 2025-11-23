package _03_Functions

fun someFooWithDefaults(isActive: Boolean = true, value: String = "default") {
    if (isActive) {
        println("Active")
    } else {
        println(value)
    }
}

fun main(args: Array<String>) {
    someFooWithDefaults(true, "Hello")
    someFooWithDefaults(isActive = true, value = "Hello")
    someFooWithDefaults(value = "Hello", isActive = true)
    someFooWithDefaults()
}
