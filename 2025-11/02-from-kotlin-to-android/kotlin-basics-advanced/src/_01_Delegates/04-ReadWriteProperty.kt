package _01_Delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class TrimmedString(initial: String = "") : ReadWriteProperty<Any?, String> {
    private var value: String = initial.trim()

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value.trim()
    }
}




class Form {
    var email by TrimmedString()
}


fun main() {
    val f = Form()
    f.email = "        user1@sbt.ru           "
    println(f.email)
}
