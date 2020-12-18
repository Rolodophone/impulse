import org.openrndr.MouseButton
import org.openrndr.UnfocusBehaviour
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Matrix44
import org.openrndr.math.transforms.transform
import kotlin.math.pow

lateinit var mDrawer: Drawer
var mDeltaTime: Double = 0.0

val bodies = mutableListOf<Body>()

fun main() = application {
	configure {
		width = 1080
		height = 720
		title = "Impulse"
		vsync = true
		windowResizable = true
		unfocusBehaviour = UnfocusBehaviour.THROTTLE
	}

	oliveProgram {
		var viewMatrix = Matrix44.IDENTITY

		mDrawer = drawer

		//bodies.add(CircleBody(Vector2(10.0, 20.0), 1.5))

		extend {
			mDeltaTime = deltaTime

			//show/hide cursor
			if (mouse.position in mDrawer.bounds) {
				mouse.cursorVisible = false
			}
			else {
				val position = mouse.position
				println("position1: ${mouse.position}")
				mouse.cursorVisible = true
				println("position2: ${mouse.position}")
//				mouse.position = position
				println("position3: ${mouse.position}")
			}

			//update bodies
			bodies.forEach { it.update() }

			//draw bodies
			mDrawer.clear(ColorRGBa.BLACK)
			mDrawer.strokeWeight = 0.0
			mDrawer.stroke = ColorRGBa.TRANSPARENT
			mDrawer.fill = ColorRGBa.WHITE
			mDrawer.view = viewMatrix

			bodies.forEach { it.draw() }

			//draw cursor
			mDrawer.view = Matrix44.IDENTITY
			mDrawer.model = Matrix44.IDENTITY
			mDrawer.stroke = ColorRGBa.GRAY
			mDrawer.lineSegment(mouse.position.x - 8.0, mouse.position.y, mouse.position.x + 7.0, mouse.position.y)
			mDrawer.lineSegment(mouse.position.x, mouse.position.y - 8.0, mouse.position.x, mouse.position.y + 7.0)
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