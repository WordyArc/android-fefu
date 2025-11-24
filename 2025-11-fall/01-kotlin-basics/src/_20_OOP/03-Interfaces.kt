package _20_OOP

// Interface defines "what the object can do"
interface Drawable {

    fun draw()
}

interface Movable {
    fun move(dx: Int, dy: Int)
}

// Class can implement multiple interfaces
class Player(
    var x: Int,
    var y: Int
) : Drawable, Movable {

    override fun draw() {
        println("Drawing player at ($x, $y)")
    }

    override fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }
}

fun startDrawing(drawable: Drawable) {
    drawable.draw()
}