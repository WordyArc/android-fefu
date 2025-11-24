class Student(
    val name: String,
    val age: Int,
    val university: String
) {
    fun introduce() {
        println("I am a student $name, I am $age, I study at $university")
    }
}

class Teacher(
    val name: String,
    val age: Int,
    val subject: String
) {
    fun introduce() {
        println("I am a teacher $name, I am $age, I teach $subject")
    }
}

class Manager(
    val name: String,
    val age: Int,
    val department: String
) {
    fun introduce() {
        println("I am a manager $name, I am $age, I manage $department department")
    }
}
