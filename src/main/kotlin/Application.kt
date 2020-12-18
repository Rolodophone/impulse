import org.openrndr.MouseButton
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.isolated
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

		fun scaleWithMouse(prevViewMatrix: Matrix44, scaleFactor: Double = 1.2): Matrix44 {
			val mousePositionInWorld = (viewMatrix.inversed * mouse.position.xy01).xy
			val scaledMousePositionInWorld = mousePositionInWorld * scaleFactor
			val scaledMousePosition = mouse.position * scaleFactor
			val translationInTermsOfView = mouse.position - scaledMousePosition
//			val translation = translationInTermsOfView / viewMatrix.c0r0
			val translation = mousePositionInWorld - scaledMousePositionInWorld

			mDrawer.isolated {
				model = Matrix44.IDENTITY
				view = Matrix44.IDENTITY
				fill = ColorRGBa.WHITE
				stroke = ColorRGBa.WHITE

				isolated {
					view = viewMatrix

					point(mousePositionInWorld)
					text("mousePositionInWorld", mousePositionInWorld)

					point(scaledMousePositionInWorld)
					text("scaledMousePositionInWorld", scaledMousePositionInWorld)
				}

				point(scaledMousePosition)
				text("scaledMousePosition", scaledMousePosition)

				lineSegment(Vector2(width.toDouble(), height.toDouble()), Vector2(width.toDouble(), height.toDouble()) + translationInTermsOfView)
				text("translationInTermsOfView", Vector2(width.toDouble(), height.toDouble()) + translationInTermsOfView)
			}

			return viewMatrix.transform {
				scale(scaleFactor)
				translate(translation)
			}
		}

		extend {
			//update

			mDeltaTime = deltaTime
			bodies.forEach { it.update() }

			//draw

			mDrawer.clear(ColorRGBa.BLACK)
			mDrawer.strokeWeight = 0.0
			mDrawer.stroke = ColorRGBa.TRANSPARENT

			mDrawer.view = viewMatrix //reassign the active view (OpenRNDR forgets it each frame)

			bodies.forEach { it.draw() }

			//<temp debug>

			mDrawer.view = Matrix44.IDENTITY
			mDrawer.model = Matrix44.IDENTITY
			mDrawer.text("World: " + (viewMatrix.inversed * mouse.position.xy01).toString(), 10.0, 20.0)
			mDrawer.text("View: " + (mouse.position.xy01).toString(), 10.0, 40.0)
			mDrawer.text("Scale: " + viewMatrix.c0r0.toString(), 10.0, 60.0)

			mDrawer.view = viewMatrix.transform { scale(1.2) }
			mDrawer.model = Matrix44.IDENTITY
			mDrawer.fill = ColorRGBa.RED
			bodies.forEach { it.draw() }

			mDrawer.view = scaleWithMouse(viewMatrix)
			mDrawer.model = Matrix44.IDENTITY
			mDrawer.fill = ColorRGBa.GREEN
			bodies.forEach { it.draw() }

			//</temp debug>
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

			viewMatrix = scaleWithMouse(viewMatrix, scaleFactor)
		}

		keyboard.keyDown.listen { keyEvent ->
			if (keyEvent.name == "q") {
				println("Q was pressed.")

				viewMatrix = viewMatrix.transform {

				}
			}
		}
	}
}