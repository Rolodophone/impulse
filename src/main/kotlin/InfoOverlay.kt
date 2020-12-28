import org.openrndr.color.ColorRGBa
import org.openrndr.color.rgba
import org.openrndr.panel.elements.round

object InfoOverlay {
	val displayText = mutableListOf("")

	fun update() {
		//update looking at
		val lookingAtCoords = cameraMatrix.inversed * pg.mouse.position.xy01
		displayText[0] = "Looking at (${ lookingAtCoords.x.round(1) }m, ${ lookingAtCoords.y.round(1) }m)"
	}

	fun draw() {
		with(pg.drawer) {
			fill = rgba(0.0, 0.0, 0.0, 0.8)
			stroke = null

			for ((i, displayString) in displayText.withIndex()) {
				rectangle(worldViewBounds.x, worldViewBounds.y + 3.0 + i*15, 8.0 + displayString.length * 7.3, 15.0)
			}

			fill = ColorRGBa.WHITE

			for ((i, displayString) in displayText.withIndex()) {
				text(displayString, worldViewBounds.x + 5, worldViewBounds.y + 15 + i*15)
			}
		}
	}
}