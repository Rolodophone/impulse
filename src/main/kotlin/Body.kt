import org.openrndr.color.ColorRGBa
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.transforms.transform
import org.openrndr.shape.Shape

abstract class Body {

    /**
     * The center of mass of the body. This is the origin for the dimensions for `shape`.
     */
    abstract var centerOfMass: Vector2

    /**
     * The shape of the body. The origin is given by `centerOfMass`.
     */
    abstract val shape: Shape

    var velocity = Vector2.ZERO
    var acceleration = Vector2.ZERO
    lateinit var modelMatrix: Matrix44

    open fun update() {
        velocity += acceleration * mDeltaTime
        centerOfMass += velocity * mDeltaTime

        modelMatrix = transform { translate(centerOfMass) }
    }

    fun draw() {
        mDrawer.fill = ColorRGBa.WHITE
        mDrawer.model = modelMatrix
        mDrawer.shape(shape)
    }

    fun applyForce(force: Vector2) {

    }
}