import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.transforms.transform
import org.openrndr.shape.Shape

abstract class Body: Entity {

    /**
     * The origin for the dimensions for [shape].
     */
    abstract var position: Vector2

    /**
     * The shape of the body. The origin is given by [position].
     */
    abstract val shape: Shape

    var velocity = Vector2.ZERO
    var acceleration = Vector2.ZERO

    private lateinit var modelMatrix: Matrix44


    override fun update() {
        velocity += acceleration * pg.deltaTime
        position += velocity * pg.deltaTime

        modelMatrix = transform { translate(position) }
    }

    override fun draw() {
        pg.drawer.model = modelMatrix
        pg.drawer.shape(shape)
    }

    fun applyForce(force: Vector2) {

    }
}