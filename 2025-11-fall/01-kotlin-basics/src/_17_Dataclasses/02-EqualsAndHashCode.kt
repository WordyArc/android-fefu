package _17_Dataclasses.EqAndHashCode

/**
 * Если a.equals(b) == true, то a.hashCode() обязан быть равен b.hashCode()
 * */

data class DataUser(val name: String, val age: Int)
class User(val name: String, val age: Int)


fun main() {
    val u1 = DataUser("Alex", 20)
    val u2 = DataUser("Alex", 20)

    val set = hashSetOf(u1, u2)
    println(set.size)
    println(set)




//    val u1 = User("Alex", 20)
//    val u2 = User("Alex", 20)
//
//    val set = hashSetOf(u1, u2)
//    println(set.size)
}


