package actions

import gui.Gui

interface Action {
    val name: String
    fun execute(gui: Gui)
    fun undo(){}
}