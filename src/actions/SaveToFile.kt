package actions

import gui.Gui
import java.io.File


class SaveToFile : Action {

    override val name: String
        get() = "SaveToFile"

    override fun execute(gui: Gui) {
        gui.saveToFile()
    }

    override fun undo(gui: Gui): Boolean {
        if(gui.createdFiles.size > 0) {
            File(gui.createdFiles.removeLast()).delete()
            return true
        }
        return false
    }

}