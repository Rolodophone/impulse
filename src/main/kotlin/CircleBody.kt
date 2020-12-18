import org.openrndr.math.Vector2
import org.openrndr.shape.Circle

class CircleBody(center: Vector2, radius: Double): Body() {
    override var position = center
    override val shape = Circle(Vector2.ZERO, radius).shape
}