import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.transforms.transform
import org.openrndr.shape.Shape

abstract class Body {

    /**
     * The origin for the dimensions for `shape`.
     */
    abstract var position: Vector2

    /**
     * The shape of the body. The origin is given by `position`.
     */
    abstract val shape: Shape

    var velocity = Vector2.ZERO
    var acceleration = Vector2.ZERO

    private lateinit var modelMatrix: Matrix44


    open fun update() {
        velocity += acceleration * mDeltaTime
        position += velocity * mDeltaTime

        modelMatrix = transform { translate(position) }
    }

    fun draw() {
        mDrawer.model = modelMatrix
        mDrawer.shape(shape)
    }

    fun applyForce(force: Vector2) {

    }
}