package visitor

import jsonElements.*
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.TreeItem
import plugins.Plugin

class BuildTreeVisitor(private val root: TreeItem, private val plugin: Plugin ): Visitor {
    private var currItem : TreeItem = root
    private var nChildren: Int = if(currItem.data is JsonObject)
                                    (currItem.data as JsonObject).nElements()
                                 else 0

    override fun visit(bol: JsonBoolean) {
        if(nChildren == 0) {
            currItem = currItem.parentItem
            nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
        }
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = bol
        newItem.text = bol.getObjectName()
        newItem.image = plugin.getFileImage()
        nChildren--
    }

    override fun visit(ch: JsonChar) {
        if(nChildren == 0) {
            currItem = currItem.parentItem
            nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
        }
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = ch
        newItem.text = ch.getObjectName()
        newItem.image = plugin.getFileImage()
        nChildren--
    }

    override fun visit(col: JsonArray) {

    }

    override fun visit(enum: JsonEnum) {
        if(nChildren == 0) {
            currItem = currItem.parentItem
            nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
        }
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = enum
        newItem.text = enum.getObjectName()
        newItem.image = plugin.getFileImage()
        nChildren--
    }

    override fun visit(num: JsonNumber) {
        if(nChildren == 0) {
            currItem = currItem.parentItem
            nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
        }
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = num
        newItem.text = num.getObjectName()
        newItem.image = plugin.getFileImage()
        nChildren--
    }

    override fun visit(obj: JsonObject) {
        if(nChildren == 0) {
            currItem = currItem.parentItem
            nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
        }
        if(obj != currItem.data) {
            val newItem = TreeItem(currItem, SWT.NONE)
            newItem.data = obj
            newItem.text = obj.getObjectName()
            newItem.image = plugin.getFolderImage()
            currItem = newItem
            nChildren = obj.nElements()
        }
    }

    override fun visit(str: JsonString) {
        if (nChildren == 0) {
            currItem = currItem.parentItem
            nChildren = (currItem.data as JsonObject).nElements() - currItem.itemCount
        }

        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = str
        newItem.text = str.getObjectName()
        newItem.image = plugin.getFileImage()
        nChildren--

    }
}
