package actions

import gui.Gui

interface Action {
    val name: String
    fun execute(gui: Gui)
    fun undo(gui: Gui):Boolean{ return true } // Return true if it ran successfully
}