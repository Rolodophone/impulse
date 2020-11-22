import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2
import org.openrndr.math.transforms.buildTransform
import org.openrndr.shape.Shape

abstract class Body {

    abstract var centerOfMass: Vector2
    abstract var shape: Shape
    var velocity = Vector2.ZERO
    var acceleration = Vector2.ZERO

    open fun update() {
        velocity += acceleration * mDeltaTime
        shape = shape.transform(buildTransform { translate(velocity * mDeltaTime) })
        centerOfMass += velocity * mDeltaTime
    }

    fun draw() {
        mDrawer.fill = ColorRGBa.WHITE
        mDrawer.shape(shape)
    }

    fun applyForce(force: Vector2) {

    }
}