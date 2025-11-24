package _12_Properties

val constant = 42

var counter = 0

fun inc() {
  counter++
}

fun main() {
    inc()

    val clazz =  Class.forName("_12_Properties._02_TopLevelPropertyKt")
    println("Class: ${clazz.name}")

    println("Fields:")
    clazz.declaredFields.forEach { f ->
        println("  ${f.name} : ${f.type}")
    }

    println("Methods:")
    clazz.declaredMethods.forEach { m ->
        println("  ${m.name}(${m.parameterCount} params)")
    }
}