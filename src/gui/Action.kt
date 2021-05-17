package gui

interface Action {
    val name: String
    fun execute()
    fun undo()
}