package _11_CreatingClasses

class Cat {
  fun meow() = "mrrrow!"
}

fun main() {
  val cat = Cat()
  val m1 = cat.meow()
  println(m1)
}
