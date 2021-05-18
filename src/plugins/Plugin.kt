package plugins

import org.eclipse.swt.graphics.Image

interface Plugin {
    fun getFolderImage(): Image?
    fun getFileImage(): Image?
}