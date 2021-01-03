import org.openrndr.panel.elements.TextElement
import org.openrndr.panel.elements.TextNode
import org.openrndr.panel.elements.round

object InfoOverlay {
	val displayText = mutableListOf("")

	fun update() {
		//update looking at
		val lookingAtCoords = cameraMatrix.inversed * pg.mouse.position.xy01
		displayText[0] = "Looking at (${ lookingAtCoords.x.round(1) }m, ${ lookingAtCoords.y.round(1) }m)"


		//show changes in UI
		val elem = findElem<TextElement>("info-text")
		elem.replaceText(displayText.joinToString("\n"))

		//update position of element
		val textNode = elem.children.first { it is TextNode } as TextNode
		elem.layout.screenY = pg.height - textNode.sizeHint().height
	}
}