import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector4

object Ground: Entity {


	override fun draw() {
		pg.drawer.isolated {
			view = Matrix44.IDENTITY
			fill = ColorRGBa.WHITE
			stroke = ColorRGBa.TRANSPARENT
			strokeWeight = 0.0
			rectangle(0.0, (cameraMatrix * Vector4(0.0, 0.0, 0.0, 1.0)).y, width.toDouble(), height - (cameraMatrix * Vector4(0.0, 0.0, 0.0, 1.0)).y)
		}
	}
}