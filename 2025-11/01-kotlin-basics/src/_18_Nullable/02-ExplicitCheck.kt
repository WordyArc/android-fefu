package _18_Nullable

import utils.eq

fun main() {
  val s: String? = "abc"
  if (s != null)
    s.length eq 3
}