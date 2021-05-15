package visitor

import jsonElements.*
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.TreeItem

class BuildTreeVisitor(private val root: TreeItem): Visitor {
    private var currItem : TreeItem = root
    private var nChildren: Int = if(currItem.data is JsonObject)
                                    (currItem.data as JsonObject).nElements()
                                 else 0

    override fun visit(bol: JsonBoolean) {
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = bol
        newItem.text = bol.getObjectName()
    }

    override fun visit(ch: JsonChar) {
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = ch
        newItem.text = ch.getObjectName()
    }

    override fun visit(col: JsonArray) {

    }

    override fun visit(enum: JsonEnum) {
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = enum
        newItem.text = enum.getObjectName()
    }

    override fun visit(num: JsonNumber) {
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = num
        newItem.text = num.getObjectName()
    }

    override fun visit(obj: JsonObject) {
        if(nChildren == 0)
            currItem = currItem.parentItem
        if(obj != currItem.data) {
            val newItem = TreeItem(currItem, SWT.NONE)
            newItem.data = obj
            newItem.text = obj.getObjectName()
            currItem = newItem
        }
    }

    override fun visit(str: JsonString) {
        val newItem = TreeItem(currItem, SWT.NONE)
        newItem.data = str
        newItem.text = str.getObjectName()
    }
}
