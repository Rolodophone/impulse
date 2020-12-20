import org.openrndr.color.ColorRGBa
import org.openrndr.panel.controlManager
import org.openrndr.panel.elements.button
import org.openrndr.panel.elements.div
import org.openrndr.panel.style.*

val UIControlManager = pg.controlManager {

	styleSheet(has class_ "side-bar") {
		this.height = 100.percent
		this.width = 200.px
		this.display = Display.FLEX
		this.flexDirection = FlexDirection.Column
		this.paddingLeft = 10.px
		this.paddingRight = 10.px
		this.background = Color.RGBa(ColorRGBa.GRAY)
	}

	styleSheet(has class_ "horiz-line") {

	}

	layout {
		div("side-bar") {
			div("tabs") {
				button {}
			}

			div("horiz-line") {}
		}
	}
}