package actions

import gui.Gui
import jsonElements.JsonElement
import visitor.SerializeVisitor
import java.lang.Exception

class Edit : Action {

    private var previousName =""
    private val map = mutableMapOf<String,JsonElement>()

    override val name: String
        get() = "Edit"

    override fun execute(gui: Gui) {
        try {
            gui.openEditWindow(gui.fileTree.selection.first())
            previousName = gui.fileTree.selection.first().text
            map[previousName] = gui.fileTree.selection.first().data as JsonElement
        } catch (e: Exception){
            gui.actionsDone.pop()
        }
    }

    override fun undo(gui: Gui) : Boolean{
        try {
            return if (map[previousName] == gui.fileTree.selection.first().data) {
                map[previousName]?.name = previousName
                map.remove(previousName)
                gui.fileTree.selection.first().text = previousName
                val serializer = SerializeVisitor()
                (gui.fileTree.selection.first().data as JsonElement).accept(serializer)
                gui.text.text = serializer.stringToReturn
                if (map.isNotEmpty())
                    previousName = map.keys.last()
                true
            } else
                false
        }catch (e: Exception){
            return false
        }

    }
}