package actions

import gui.Gui
import jsonElements.JsonArray
import jsonElements.JsonElement
import jsonElements.JsonObject
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.TreeItem
import visitor.BuildTreeVisitor

class RemoveNoName: Action {


    private lateinit var root : JsonElement

    override val name: String
        get() = "Remove no name"

    override fun execute(gui: Gui) {
        val numItems = gui.fileTree.items.size
        val rootItem = gui.fileTree.items[0]
        if(rootItem.data is JsonElement)
            root = rootItem.data as JsonElement
        val listToRemove = mutableListOf<TreeItem>()
        for (i in 0 until numItems)
            if(gui.fileTree.items[i].text != "")
                checkIfRemove(gui.fileTree.items[i],listToRemove)
            else if(i == 0 && numItems == 1) {
                listToRemove.add(rootItem)
                gui.fileTree.removeAll()
            }
    }


    private fun checkIfRemove(treeItem :TreeItem, listToRemove : MutableList<TreeItem>) {
        treeItem.items.forEach {
            if (it.text == "") {
                listToRemove.add(it.parentItem)
            } else {
                if (it.data is JsonObject || it.data is JsonArray)
                    checkIfRemove(it,listToRemove)
            }
        }
        listToRemove.forEach {
            it.removeAll()
        }
    }

    override fun undo(gui: Gui): Boolean {
        gui.fileTree.removeAll()
        val rootItem = TreeItem(gui.fileTree, SWT.NONE)
        rootItem.data = root
        rootItem.text = root.name
        rootItem.image = gui.plugin.getFileImage()
        val visitor = BuildTreeVisitor(rootItem, gui.plugin)
        if (root is JsonArray) {
            if ((root as JsonArray).containsObject())
                rootItem.image = gui.plugin.getFolderImage()
            root.accept(visitor)
        } else if (root is JsonObject) {
            rootItem.image = gui.plugin.getFolderImage()
            root.accept(visitor)
        }
        return true
    }

}