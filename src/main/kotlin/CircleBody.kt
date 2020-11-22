import org.openrndr.math.Vector2
import org.openrndr.shape.Circle

class CircleBody(center: Vector2, radius: Double): Body() {
    override var centerOfMass = center
    override var shape = Circle(center, radius).shape
}