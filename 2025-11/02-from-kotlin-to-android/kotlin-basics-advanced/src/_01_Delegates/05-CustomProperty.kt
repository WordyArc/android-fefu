package _01_Delegates

import kotlin.reflect.KProperty

class SimpleBox<T>(initial: T) {
    private var v: T = initial

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = v
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) { v = value }
}


class NonNegativeInt(initial: Int = 0) {
    private var v = initial.coerceAtLeast(0)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int = v
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        v = value.coerceAtLeast(0)
    }
}


class Stats {
    var score: Int by NonNegativeInt()
}



fun main() {
    val stats = Stats()

    stats.score = 12312
    println(stats.score)
}