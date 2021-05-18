package actions

import gui.Gui

class Edit : Action {
    override val name: String
        get() = "Edit"

    override fun execute(gui: Gui) {
        gui.edit()
    }

    override fun undo() {

    }
}