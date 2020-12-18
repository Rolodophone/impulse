import org.openrndr.MouseButton
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.transforms.transform
import kotlin.math.pow

lateinit var mDrawer: Drawer
var mDeltaTime: Double = 0.0

val bodies = mutableListOf<Body>()

fun main() = application {
	configure {
		width = 1080
		height = 720
	}
	oliveProgram {
		var viewMatrix = Matrix44.IDENTITY

		mDrawer = drawer

		bodies.add(CircleBody(Vector2(10.0, 20.0), 1.5))

		extend {
			mDeltaTime = deltaTime

			//------update------

			bodies.forEach { it.update() }

			//------draw-------

			mDrawer.clear(ColorRGBa.BLACK)
			mDrawer.strokeWeight = 0.0
			mDrawer.stroke = ColorRGBa.TRANSPARENT
			mDrawer.fill = ColorRGBa.WHITE
			mDrawer.view = viewMatrix //reassign the active view (OpenRNDR forgets it each frame)

			bodies.forEach { it.draw() }
		}

		mouse.dragged.listen { mouseEvent ->
			if (mouseEvent.button == MouseButton.RIGHT) {
				//convert mouse displacement in view coordinates to displacement in world coordinates
				val translation = mouseEvent.dragDisplacement / viewMatrix.c0r0
				viewMatrix = viewMatrix.transform { translate(translation) }
			}
		}

		mouse.scrolled.listen { mouseEvent ->
			val scaleFactor = 1.2.pow(mouseEvent.rotation.y)

			val mousePositionInWorld = (viewMatrix.inversed * mouse.position.xy01).xy
			val scaledMousePositionInWorld = mousePositionInWorld * scaleFactor
			val translation = mousePositionInWorld - scaledMousePositionInWorld

			viewMatrix = viewMatrix.transform {
				translate(translation)
				scale(scaleFactor)
			}
		}
	}
}