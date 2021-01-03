inline fun <reified T> findElem(id: String): T {
	val elem: T?

	try {
		elem = UIControlManager.body?.elementWithId<T>(id)
	}
	catch (e: NullPointerException) {
		throw NullPointerException("Error: $e (most likely element doesn't exist)")
	}

	if (elem != null) return elem
	else throw NoSuchElementException("No such element: $id")
}