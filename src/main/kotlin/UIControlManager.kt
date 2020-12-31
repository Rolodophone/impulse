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
		background = Color.RGBa(ColorRGBa.GRAY)
	}

	styleSheet(has class_ "tab-button") {
		borderWidth = 1.px
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
				button("tab-button", "options-button") {
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
				button("tab-button", "edit-button") {
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
				button("tab-button", "view-button") {
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
				toggle {
					id = "ground-button"
					label = "Ground"
					value = true
					events.valueChanged.listen {
						if (value) entities.add(Ground)
						else entities.remove(Ground)
					}
				}
			}

			div("edit-tab") {
			}

			div("view-tab") {
				toggle {
					id = "grid-button"
					label = "Grid"
					value = true
				}
			}
		}
	}
}


inline fun <reified T> findElem(id: String) = UIControlManager.body!!.elementWithId<T>(id)!!