package gui

import Inject
import InjectAdd
import actions.Action
import jsonElements.JsonElement
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.layout.RowData
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.widgets.*
import plugins.Plugin
import visitor.BuildTreeVisitor
import java.util.*

class Gui {
    val shell: Shell
    val fileTree: Tree
    @InjectAdd
    private val actions = mutableListOf<Action>()
    @Inject
    private lateinit var plugin : Plugin
    private val actionsDone = Stack<Action>()
    private val buttonRow:Composite
    private val infoRow:Composite
    init {

        shell = Shell(Display.getDefault())
        shell.text = "PAProject"
        shell.layout = RowLayout()

        infoRow = Composite(shell, SWT.SINGLE)
        infoRow.layout = GridLayout(2, true)
        infoRow.layoutData = RowData(shell.size.x,shell.size.y - 100)

        buttonRow = Composite(shell, SWT.SINGLE)
        buttonRow.layout = RowLayout()
        buttonRow.layoutData = RowData(shell.size.x,75)

        fileTree = Tree(infoRow, SWT.SINGLE or SWT.BORDER)
        fileTree.layoutData = GridData(SWT.FILL, SWT.FILL, true, true)

        val text = Text(infoRow, SWT.MULTI)
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
        rootItem.image = plugin.getFolderImage()

        val visitor = BuildTreeVisitor(rootItem, plugin)
        root.accept(visitor)

        setupButtons()

        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()
    }

    private fun setupButtons() {
        actions.forEach { action ->
            val button = Button(buttonRow, SWT.PUSH)
            button.text = action.name
            button.addSelectionListener( object : SelectionAdapter() {
                override fun widgetSelected(e: SelectionEvent) {
                    actionsDone.push(action)
                    action.execute(this@Gui)
                }
            })
        }
    }

    fun undo(){
        if(actionsDone.size > 0) {
            val actionDone = actionsDone.pop()
            actionDone.undo()
        }
    }

    fun edit(){
        fileTree.selection.first().text
    }
}