package _18_Nullable

import utils.eq

fun main() {
    val s1 = "abc"

    // Compile-time error:
    // val s2: String = null

    // Nullable definitions:
    val s3: String? = null
    val s4: String? = s1

    // Compile-time error:
    // val s5: String = s4
    val s6 = s4

    s1 eq "abc"
    s3 eq null
    s4 eq "abc"
    s6 eq "abc"
}