import org.openrndr.draw.isolated
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.Vector4

object Ground: Entity {

	override fun affect() {
		entities.forEach {
			if (it is Body) it.exertForce(Vector2(0.0, 9.81))
		}
	}

	override fun draw() {
		pg.drawer.isolated {
			model = Matrix44.IDENTITY
			view = Matrix44.IDENTITY
			rectangle(worldViewBounds.x, (cameraMatrix * Vector4(0.0, 0.0, 0.0, 1.0)).y, worldViewBounds.width, height - (cameraMatrix * Vector4(0.0, 0.0, 0.0, 1.0)).y)
		}
	}
}