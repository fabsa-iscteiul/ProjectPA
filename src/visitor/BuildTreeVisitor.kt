package visitor

import jsonElements.*
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.TreeItem
import plugins.Plugin

class BuildTreeVisitor(private val root: TreeItem, private val plugin: Plugin ): Visitor {
    private var currItem : TreeItem = root
    private var nChildren: Int = if(currItem.data is JsonObject)
                                    (currItem.data as JsonObject).nElements()
                                 else if(currItem.data is JsonArray)
                                    (currItem.data as JsonArray).nElements()
                                 else 0

    override fun visit(bol: JsonBoolean) {
        while(nChildren == 0) {
            currItem = currItem.parentItem
            if(currItem.data is JsonObject)
                nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
            else if(currItem.data is JsonArray)
                nChildren = (currItem.data as JsonArray).nElements() - currItem.itemCount
        }
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = bol
        newItem.text = bol.name
        newItem.image = plugin.getFileImage()
        nChildren--
    }

    override fun visit(ch: JsonChar) {
        while(nChildren == 0) {
            currItem = currItem.parentItem
            if(currItem.data is JsonObject)
                nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
            else if(currItem.data is JsonArray)
                nChildren = (currItem.data as JsonArray).nElements() - currItem.itemCount
        }
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = ch
        newItem.text = ch.name
        newItem.image = plugin.getFileImage()
        nChildren--
    }

    override fun visit(col: JsonArray) {
        while(nChildren == 0) {
            currItem = currItem.parentItem
            if(currItem.data is JsonObject)
                nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
            else if(currItem.data is JsonArray)
                nChildren = (currItem.data as JsonArray).nElements() - currItem.itemCount
        }
        if(col != currItem.data) {
            val newItem = TreeItem(currItem, SWT.NONE)
            newItem.data = col
            newItem.text = col.name
            if(col.containsObject())
                newItem.image = plugin.getFolderImage()
            else
                newItem.image = plugin.getFileImage()
            currItem = newItem
            nChildren = col.nElements()
        }
    }

    override fun visit(enum: JsonEnum) {
        while(nChildren == 0) {
            currItem = currItem.parentItem
            if(currItem.data is JsonObject)
                nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
            else if(currItem.data is JsonArray)
                nChildren = (currItem.data as JsonArray).nElements() - currItem.itemCount
        }
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = enum
        newItem.text = enum.name
        newItem.image = plugin.getFileImage()
        nChildren--
    }

    override fun visit(num: JsonNumber) {
        while(nChildren == 0) {
            currItem = currItem.parentItem
            if(currItem.data is JsonObject)
                nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
            else if(currItem.data is JsonArray)
                nChildren = (currItem.data as JsonArray).nElements() - currItem.itemCount
        }
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = num
        newItem.text = num.name
        newItem.image = plugin.getFileImage()
        nChildren--
    }

    override fun visit(obj: JsonObject) {
        while(nChildren == 0) {
            currItem = currItem.parentItem
            if(currItem.data is JsonObject)
                nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
            else if(currItem.data is JsonArray)
                nChildren = (currItem.data as JsonArray).nElements() - currItem.itemCount
        }
        if(obj != currItem.data) {
            val newItem = TreeItem(currItem, SWT.NONE)
            newItem.data = obj
            newItem.text = obj.name
            newItem.image = plugin.getFolderImage()
            currItem = newItem
            nChildren = obj.nElements()
        }
    }

    override fun visit(str: JsonString) {
        while (nChildren == 0) {
            currItem = currItem.parentItem
            if(currItem.data is JsonObject)
                nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
            else if(currItem.data is JsonArray)
                nChildren = (currItem.data as JsonArray).nElements() - currItem.itemCount
        }

        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = str
        newItem.text = str.name
        newItem.image = plugin.getFileImage()
        nChildren--

    }
}
