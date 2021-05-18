package plugins

import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Display

class DefaultPlugin : Plugin {
    override fun getFileImage(): Image? {
        return null
    }

    override fun getFolderImage(): Image? {
        return null
    }
}