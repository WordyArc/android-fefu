package _18_Nullable

import utils.eq

fun main() {
  val s: String? = "abc"
  s!!.length eq 3
}