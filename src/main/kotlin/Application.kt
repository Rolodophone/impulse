import org.openrndr.MouseButton
import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import org.openrndr.math.transforms.transform
import org.openrndr.panel.elements.Toggle
import org.openrndr.shape.Rectangle
import kotlin.math.pow
import kotlin.random.Random.Default.nextDouble

lateinit var pg: Program

val entities = mutableListOf<Entity>()

lateinit var worldViewBounds: Rectangle

var cameraMatrix = transform { scale(200.0) }

fun main() = application {
	configure {
		width = 1080
		height = 720
		title = "Impulse"
		vsync = true
		windowResizable = true
	}

	oliveProgram {
		pg = this
		worldViewBounds = Rectangle(260.0, 0.0, width - 260.0, height.toDouble())

		//temp - add example bodies
		entities.addAll(List(10) { CircleBody(
			Vector2(nextDouble(width / cameraMatrix.c0r0), -nextDouble(height / cameraMatrix.c0r0)),
			nextDouble(0.1, 0.5)
		) })


		//handle UI
		extend(UIControlManager)


		//main loop
		extend {
			//update bodies
			entities.forEach { it.affect() }
			entities.forEach { it.react() }

			//update info overlay
			InfoOverlay.update()

			//draw bodies
			drawer.isolated {
				stroke = null
				fill = ColorRGBa.WHITE
				view = cameraMatrix

				entities.forEach { it.draw() }
			}

			//draw grid
			if (findElem<Toggle>("grid-button").value) {
				drawer.isolated { Grid.draw(cameraMatrix) }
			}

			//draw info overlay
			drawer.isolated { InfoOverlay.draw() }
		}


		//handle panning
		mouse.dragged.listen { mouseEvent ->
			if (mouseEvent.button == MouseButton.RIGHT && mouse.position in worldViewBounds) {
				//convert mouse displacement in view coordinates to displacement in world coordinates
				val translation = mouseEvent.dragDisplacement / cameraMatrix.c0r0
				cameraMatrix = cameraMatrix.transform { translate(translation) }
			}
		}


		//handle zooming with scroll wheel
		mouse.scrolled.listen { mouseEvent ->
			if (mouse.position in worldViewBounds) {
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
}