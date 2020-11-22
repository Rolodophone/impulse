import org.openrndr.Extension
import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.isolated
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle

lateinit var mDrawer: Drawer
var mDeltaTime: Double = 0.0

val bodies = mutableListOf<Body>()

fun main() = application {
    configure {
        width = 1080
        height = 720
    }
    oliveProgram {
        mDrawer = drawer

        bodies.add(CircleBody(Vector2(500.0, 400.0), 100.0))
        bodies[0].velocity = Vector2(50.0, 100.0)

        extend {
            //update
            mDeltaTime = deltaTime
            bodies.forEach { it.update() }

            //draw
            mDrawer.clear(ColorRGBa.BLACK)
            mDrawer.strokeWeight = 0.0
            mDrawer.stroke = ColorRGBa.TRANSPARENT

            bodies.forEach { it.draw() }
        }
    }
}