import org.openrndr.color.ColorRGBa
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.panel.elements.round
import org.openrndr.shape.LineSegment

object Grid {
	var divisionSize = 1.0

	fun draw(cameraMatrix: Matrix44) {
		val scaledDivisionSize = divisionSize * cameraMatrix.c0r0
		val origin = (cameraMatrix * Vector2.ZERO.xy01).xy

		val xList = mutableListOf<Double>()
		var x = origin.x % scaledDivisionSize
		while (x < pg.width) {
			xList.add(x.round(0))
			x += scaledDivisionSize
		}

		val yList = mutableListOf<Double>()
		var y = origin.y % scaledDivisionSize
		while (y < pg.height) {
			yList.add(y.round(0))
			y += scaledDivisionSize
		}

		pg.drawer.stroke = ColorRGBa(.2, .2, .2)
		pg.drawer.strokeWeight = 1.0
		pg.drawer.drawStyle.smooth = false

		pg.drawer.lineSegments(List(xList.size) { i -> LineSegment(xList[i], 0.0, xList[i], pg.height.toDouble()) })
		pg.drawer.lineSegments(List(yList.size) { i -> LineSegment(0.0, yList[i], pg.width.toDouble(), yList[i]) })
	}
}