package _20_OOP.inheritance

open class Person(
    val name: String,
    val age: Int
) {
    open fun introduce() {
        println("I am $name, I am $age years old")
    }
}

class Student(
    name: String,
    age: Int,
    val university: String
) : Person(name, age) {

    override fun introduce() {
        println("I am a student $name, I am $age, I study at $university")
    }
}

class Teacher(
    name: String,
    age: Int,
    val subject: String
) : Person(name, age) {

    override fun introduce() {
        println("I am a teacher $name, I am $age, I teach $subject")
    }
}
