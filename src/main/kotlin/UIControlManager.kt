import org.openrndr.color.ColorRGBa
import org.openrndr.panel.controlManager
import org.openrndr.panel.elements.button
import org.openrndr.panel.elements.div
import org.openrndr.panel.elements.toggle
import org.openrndr.panel.style.*

val UIControlManager = pg.controlManager {
	styleSheet(has class_ "side-bar") {
		height = 100.percent
		width = 260.px
		display = Display.FLEX
		flexDirection = FlexDirection.Column
		paddingLeft = 7.px
		paddingRight = 7.px
		background = Color.RGBa(ColorRGBa.GRAY)
	}

	styleSheet(has class_ "edit-button") {
		borderColor = Color.RGBa(ColorRGBa.WHITE)
	}

	styleSheet(has class_ "horiz-line") {
		height = 1.px
		width = 100.percent
		background = Color.RGBa(ColorRGBa.WHITE)
	}

	styleSheet(has class_ "options-tab") {
		display = Display.NONE
	}

	styleSheet(has class_ "view-tab") {
		display = Display.NONE
	}

	layout {
		div("side-bar") {
			div {
				button("options-button") {
					id = "options-button"
					label = "Options"
					events.clicked.listen {
						styleSheet(has class_ "options-button") { borderColor = Color.RGBa(ColorRGBa.WHITE) }
						styleSheet(has class_ "edit-button") { borderColor = Color.RGBa(ColorRGBa.TRANSPARENT) }
						styleSheet(has class_ "view-button") { borderColor = Color.RGBa(ColorRGBa.TRANSPARENT) }
						styleSheet(has class_ "options-tab") { display = Display.BLOCK }
						styleSheet(has class_ "edit-tab") { display = Display.NONE }
						styleSheet(has class_ "view-tab") { display = Display.NONE }
					}
				}
				button("edit-button") {
					id = "edit-button"
					label = "Edit"
					events.clicked.listen {
						styleSheet(has class_ "options-button") { borderColor = Color.RGBa(ColorRGBa.TRANSPARENT) }
						styleSheet(has class_ "edit-button") { borderColor = Color.RGBa(ColorRGBa.WHITE) }
						styleSheet(has class_ "view-button") { borderColor = Color.RGBa(ColorRGBa.TRANSPARENT) }
						styleSheet(has class_ "options-tab") { display = Display.NONE }
						styleSheet(has class_ "edit-tab") { display = Display.BLOCK }
						styleSheet(has class_ "view-tab") { display = Display.NONE }
					}
				}
				button("view-button") {
					id = "view-button"
					label = "View"
					events.clicked.listen {
						styleSheet(has class_ "options-button") { borderColor = Color.RGBa(ColorRGBa.TRANSPARENT) }
						styleSheet(has class_ "edit-button") { borderColor = Color.RGBa(ColorRGBa.TRANSPARENT) }
						styleSheet(has class_ "view-button") { borderColor = Color.RGBa(ColorRGBa.WHITE) }
						styleSheet(has class_ "options-tab") { display = Display.NONE }
						styleSheet(has class_ "edit-tab") { display = Display.NONE }
						styleSheet(has class_ "view-tab") { display = Display.BLOCK }
					}
				}
			}

			div("horiz-line") {}

			div("options-tab") {
			}

			div("edit-tab") {
			}

			div("view-tab") {
				toggle {
					id = "grid-button"
					label = "Grid"
				}
			}
		}
	}
}


inline fun <reified T> findElem(id: String) = UIControlManager.body!!.elementWithId<T>(id)!!