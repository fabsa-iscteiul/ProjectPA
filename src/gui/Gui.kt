package gui

import Inject
import InjectAdd
import actions.Action
import actions.Undo
import jsonElements.JsonElement
import org.eclipse.swt.SWT
import org.eclipse.swt.events.KeyAdapter
import org.eclipse.swt.events.KeyEvent
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.layout.RowData
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.widgets.*
import plugins.Plugin
import visitor.BuildTreeVisitor
import visitor.SearchNameVisitor
import visitor.SerializeVisitor
import java.io.File
import java.util.*

class Gui {
    val shell: Shell = Shell(Display.getDefault())
    val fileTree: Tree
    @InjectAdd
    private val actions = mutableListOf<Action>()
    @Inject
    private lateinit var plugin : Plugin
    val actionsDone = Stack<Action>()
    private val buttonRow:Composite
    private val infoRow:Composite
    private val serializer= SerializeVisitor()
    val text : Text
    val createdFiles = mutableListOf<String>()

    init {

        shell.text = "PAProject"
        shell.layout = RowLayout()

        infoRow = Composite(shell, SWT.SINGLE)
        infoRow.layout = GridLayout(2, true)
        infoRow.layoutData = RowData(shell.size.x,shell.size.y - 125)

        fileTree = Tree(infoRow, SWT.SINGLE or SWT.BORDER)
        fileTree.layoutData = GridData(SWT.FILL, SWT.FILL, true, true)

        val searchRow = Composite(shell, SWT.SINGLE)
        searchRow.layout = GridLayout(2,false)

        val label  = Label(searchRow, SWT.SINGLE)
        label.text = "Search: "

        val search = Text(searchRow, SWT.SINGLE)
        search.size = Point(200,30)
        search.editable=true
        search.addKeyListener(object : KeyAdapter(){
            override fun keyPressed(e: KeyEvent?) {
                val searchVisitor: SearchNameVisitor = if (e?.keyCode != 8)
                    SearchNameVisitor(search.text + "" + e?.character)
                else if(search.text == "")
                    SearchNameVisitor("")
                else {
                    if(search.text.length == 1)
                        SearchNameVisitor("")
                    else
                        SearchNameVisitor(search.text)
                }

                fileTree.items.forEach {
                    if (it.data is JsonElement)
                        (it.data as JsonElement).accept(searchVisitor)
                }
                println(searchVisitor.list)
                if(searchVisitor.list.isNotEmpty()) {
                    searchVisitor.list.forEach {
                        highlightSearch(it, fileTree.items[0])
                    }
                }
                else
                    dehighlightSearch(fileTree.items[0])

            }
        })

        buttonRow = Composite(shell, SWT.SINGLE)
        buttonRow.layout = RowLayout()
        buttonRow.layoutData = RowData(shell.size.x,75)



        text = Text(infoRow, SWT.MULTI or SWT.V_SCROLL)
        text.layoutData = GridData(GridData.FILL_BOTH)
        text.editable = false

        fileTree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                val selectedItem = fileTree.selection.first().data
                if(selectedItem is JsonElement){
                    serializer.stringToReturn=""
                    serializer.numObj =0
                    selectedItem.accept(serializer)
                    text.text = serializer.stringToReturn
                }

            }
        })
    }

    fun open(root: JsonElement) {
        val rootItem = TreeItem(fileTree, SWT.NONE)
        rootItem.data = root
        rootItem.text = root.name
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
                    if(action !is Undo)
                        actionsDone.push(action)
                    action.execute(this@Gui)
                }
            })
        }
    }

    fun undo(){
        if(actionsDone.size > 0) {
            val actionDone = actionsDone.pop()
            if(!actionDone.undo(this@Gui))
                actionsDone.push(actionDone)
        }
    }

    fun openEditWindow(treeItem : TreeItem) {
        val editWindow = EditWindow(treeItem, shell, text, this@Gui)
        editWindow.open()
        shell.isVisible = false
    }

    fun saveToFile() {
        val fileSave = FileSaveWindow(text.text, shell, this@Gui)
        fileSave.open()
        shell.isVisible = false
    }


    private fun highlightSearch(name: String, treeItem: TreeItem){
        if(treeItem.text == name)
            treeItem.background = Color(RGB(10, 75, 120))
        treeItem.items.forEach {
            if(name == it.text)
                it.background = Color(RGB(10, 75, 120))
            else
                highlightSearch(name, it)
        }
    }

    private fun dehighlightSearch(treeItem: TreeItem){
        treeItem.background = null
        treeItem.items.forEach {
            it.background = null
            dehighlightSearch(it)
        }
    }

}
