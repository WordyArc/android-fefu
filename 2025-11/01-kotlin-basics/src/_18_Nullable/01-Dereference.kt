package _18_Nullable

import utils.eq

fun main() {
    val s1: String = "abc"
    val s2: String? = s1

    s1.length eq 3
    // Doesn't compile:
    // s2.length
}