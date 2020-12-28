import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector4

object Ground: Entity {


	override fun draw() {
		pg.drawer.isolated {
			model = Matrix44.IDENTITY
			view = Matrix44.IDENTITY
			fill = ColorRGBa.WHITE
			stroke = null
			rectangle(worldViewBounds.x, (cameraMatrix * Vector4(0.0, 0.0, 0.0, 1.0)).y, worldViewBounds.width, height - (cameraMatrix * Vector4(0.0, 0.0, 0.0, 1.0)).y)
		}
	}
}