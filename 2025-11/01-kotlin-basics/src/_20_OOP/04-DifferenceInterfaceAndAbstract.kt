package _20_OOP.diff


abstract class BaseUser(
    val id: String
) {
    var isActive: Boolean = true

    fun deactivate() {
        isActive = false
        println("User $id deactivated")
    }

    abstract fun role(): String
}

class AdminUser(id: String) : BaseUser(id) {
    override fun role(): String = "ADMIN"
}

class RegularUser(id: String) : BaseUser(id) {
    override fun role(): String = "USER"
}




















interface HasId {
    val id: String    // no backing field, impl contract
}

interface Activatable {
    var isActive: Boolean

    fun deactivate() {
        isActive = false
        println("Объект деактивирован")
    }
}

interface RoleHolder {
    fun role(): String
}


// Interfaces contract to having property
class Teacher(
    override val id: String
) : HasId, Activatable, RoleHolder {

    override var isActive: Boolean = true

    override fun role(): String = "TEACHER"
}