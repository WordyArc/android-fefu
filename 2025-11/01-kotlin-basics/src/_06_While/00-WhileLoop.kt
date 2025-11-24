package _06_While

fun condition(i: Int) = i < 100

fun main() {
    var i = 0
    while (condition(i)) {
        print(".")
        i += 10
    }
}
