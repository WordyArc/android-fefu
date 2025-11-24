package _19_Lambda

import utils.eq

fun main() {
  val list = listOf(1, 2, null)
  list.filterNotNull() eq "[1, 2]"
}
