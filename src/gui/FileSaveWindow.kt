package gui

import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import java.io.File

class FileSaveWindow(val fileContent: String, sh: Shell, gui: Gui) {

    private val shell = Shell(Display.getDefault())

    init {
        shell.text = "Save to file"
        shell.layout = GridLayout(3, false)
        shell.size = Point(250,90)
        shell.addListener(SWT.Close) {
            gui.actionsDone.pop()
            sh.isVisible = true
        }

        val label = Label(shell, SWT.NONE)
        label.text = "File path: "

        val inputArea = Text(shell, SWT.NONE)
        inputArea.editable = true

        val okButton = Button(shell, SWT.NONE)
        okButton.text = "Ok"
        okButton.addSelectionListener( object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                try {
                    File(inputArea.text).writeText(fileContent)
                    gui.createdFiles.add(inputArea.text)
                }catch (e : Exception){
                    gui.actionsDone.pop()
                }
                sh.isVisible = true
                shell.dispose()
            }
        })
    }


    fun open(){
        shell.open()
    }
}