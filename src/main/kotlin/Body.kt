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


    override fun react() {
        //apply acceleration and velocity
        velocity += acceleration * pg.deltaTime
        position += velocity * pg.deltaTime

        modelMatrix = transform { translate(position) }

        //reset acceleration
        acceleration = Vector2.ZERO
    }

    override fun draw() {
        pg.drawer.model = modelMatrix
        pg.drawer.shape(shape)
    }

    fun exertForce(force: Vector2) {
        acceleration += force
    }
}