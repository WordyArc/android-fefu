package _14_Visibility

class Cookie(
    private var isReady: Boolean
) {
    private fun crumble() =
        println("crumble")

    public fun bite() =
        println("bite")

    fun eat() {
        isReady = true
        crumble()
        bite()
    }
}

fun main() {
    val x = Cookie(false)
    x.bite()
    // Can't access private members:
    // x.isReady
    // x.crumble()
    x.eat()
}