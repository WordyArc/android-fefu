package _17_Dataclasses

data class DataUser(val name: String, val age: Int)

fun main() {
    val u1 = DataUser("Alex", 20)
    val u2 = DataUser("Alex", 20)

    println(u1 == u2)
    println(u1 === u2)
}







class UserInside(val name: String, val age: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        return name == other.name &&
                age == other.age
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        return result
    }

    override fun toString(): String {
        return "User(name=$name, age=$age)"
    }

    // + copy(), component1(), component2()
}





