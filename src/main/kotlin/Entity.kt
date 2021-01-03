import org.openrndr.math.Vector2

interface Entity {

	fun affect() {}
	fun react() {}

	fun draw() {}

	fun exertForce(force: Vector2) {}
}