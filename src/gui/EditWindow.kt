package gui

import jsonElements.JsonElement
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import visitor.SerializeVisitor

class EditWindow(val treeItem : TreeItem, sh: Shell, val text: Text, gui: Gui) {
    private val shell : Shell = Shell(Display.getDefault())

    init {
        shell.text = "Edit " + treeItem.text
        shell.layout = GridLayout(3, false)
        shell.size = Point(250,90)
        shell.addListener(SWT.Close) {
            gui.actionsDone.pop()
            sh.isVisible = true
        }

        val label = Label(shell, SWT.NONE)
        label.text = "name: "

        val inputArea = Text(shell, SWT.NONE)
        inputArea.size = Point(200, 30)
        inputArea.editable = true

        val okButton = Button(shell, SWT.NONE)
        okButton.text = "Ok"
        okButton.addSelectionListener( object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                val updatedText = inputArea.text
                if(updatedText !="") {
                    treeItem.text = updatedText
                    if (treeItem.data is JsonElement) {
                        (treeItem.data as JsonElement).name = updatedText
                        val serializer = SerializeVisitor()
                        (treeItem.data as JsonElement).accept(serializer)
                        text.text = serializer.stringToReturn
                    }
                }
                sh.isVisible = true
                shell.dispose()
            }
        })
    }

    fun open() {
        shell.open()
    }
}