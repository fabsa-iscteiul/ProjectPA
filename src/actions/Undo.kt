package actions

import gui.Gui

class Undo : Action {
    override val name: String
        get() = "Undo"

    override fun execute(gui: Gui) {
        gui.undo()
    }
}