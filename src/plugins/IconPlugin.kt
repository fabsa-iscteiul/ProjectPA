package plugins

import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Display
import plugins.Plugin

class IconPlugin : Plugin {

    private val folderIcon = Image(Display.getCurrent(),"src\\Folder-Generic-Green-icon.png")
    private val fileIcon = Image(Display.getCurrent(),"src\\FileIcon.png")

    override fun getFolderImage(): Image? {
        return folderIcon
    }

    override fun getFileImage(): Image? {
        return fileIcon
    }

}