import org.openrndr.MouseButton
import org.openrndr.Program
import org.openrndr.UnfocusBehaviour
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import org.openrndr.math.transforms.transform
import kotlin.math.pow
import kotlin.random.Random.Default.nextDouble

lateinit var pg: Program

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
		var cameraMatrix = transform { scale(200.0) }
		
		pg = this

		bodies.addAll(List(10) { CircleBody(
			Vector2(nextDouble(width / cameraMatrix.c0r0), nextDouble(height / cameraMatrix.c0r0)),
			nextDouble(0.1, 0.5)
		) })

		extend {
//			//show/hide cursor
//			if (mouse.position in drawer.bounds) {
//				mouse.cursorVisible = false
//			}
//			else {
//				val position = mouse.position
//				println("position1: ${mouse.position}")
//				mouse.cursorVisible = true
//				println("position2: ${mouse.position}")
//				mouse.position = position
//				println("position3: ${mouse.position}")
//			}

			//update bodies
			bodies.forEach { it.update() }

			//draw bodies
			drawer.isolated {
				strokeWeight = 0.0
				stroke = ColorRGBa.TRANSPARENT
				fill = ColorRGBa.WHITE
				view = cameraMatrix

				bodies.forEach { it.draw() }
			}

			//draw grid
			drawer.isolated { Grid.draw(cameraMatrix) }
//
//			//draw cursor
//			drawer.stroke = ColorRGBa.GRAY
//			drawer.lineSegment(mouse.position.x - 8.0, mouse.position.y, mouse.position.x + 7.0, mouse.position.y)
//			drawer.lineSegment(mouse.position.x, mouse.position.y - 8.0, mouse.position.x, mouse.position.y + 7.0)
		}

		mouse.dragged.listen { mouseEvent ->
			if (mouseEvent.button == MouseButton.RIGHT) {
				//convert mouse displacement in view coordinates to displacement in world coordinates
				val translation = mouseEvent.dragDisplacement / cameraMatrix.c0r0
				cameraMatrix = cameraMatrix.transform { translate(translation) }
			}
		}

		mouse.scrolled.listen { mouseEvent ->
			val scaleFactor = 1.2.pow(mouseEvent.rotation.y)

			val mousePositionInWorld = (cameraMatrix.inversed * mouse.position.xy01).xy
			val scaledMousePositionInWorld = mousePositionInWorld * scaleFactor
			val translation = mousePositionInWorld - scaledMousePositionInWorld

			cameraMatrix = cameraMatrix.transform {
				translate(translation)
				scale(scaleFactor)
			}
		}
	}
}