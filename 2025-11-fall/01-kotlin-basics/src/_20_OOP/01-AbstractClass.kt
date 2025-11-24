package _20_OOP

abstract class Animal(
    val name: String
) {
    var energy: Int = 100

    fun sleep() {
        energy += 10
        println("$name eat. Energy: $energy")
    }

    fun eat() {
        energy += 5
        println("$name eat. Energy: $energy")
    }

    abstract fun makeSound()
}

class Dog(name: String) : Animal(name) {
    override fun makeSound() {
        println("$name: Wof wof!")
    }
}

class Cat(name: String) : Animal(name) {
    override fun makeSound() {
        println("$name: Meow!")
    }
}


fun main() {
    val animals: List<Animal> = listOf(
        Dog("Sharik"),
        Cat("Kiki")
    )

    for (animal in animals) {
        animal.eat()
        animal.sleep()
        animal.makeSound()
    }

    // val a = Animal("Кто-то")
}