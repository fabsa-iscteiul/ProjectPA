package gui

import jsonElements.JsonArray
import jsonElements.JsonElement
import jsonElements.JsonObject
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import visitor.BuildTreeVisitor

class Gui {
    val shell: Shell
    val fileTree: Tree

    init {
        shell = Shell(Display.getDefault())
        shell.text = "PAProject"
        shell.layout = GridLayout(2,false)
        fileTree = Tree(shell, SWT.SINGLE or SWT.BORDER)
        fileTree.layoutData = GridData(SWT.FILL, SWT.FILL, true, true)

        val text = Text(shell, SWT.MULTI)
        text.layoutData = GridData(GridData.FILL_BOTH)
        text.editable = false
        fileTree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                val selectedItem = fileTree.selection.first().data
                if(selectedItem is JsonElement){
                    text.text = selectedItem.serialize()
                }

            }
        })
    }

    fun open(root: JsonElement) {
        val rootItem = TreeItem(fileTree, SWT.NONE)
        rootItem.data = root
        rootItem.text = root.getObjectName()
        val visitor = BuildTreeVisitor(rootItem)
        root.accept(visitor)
        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()
    }
}