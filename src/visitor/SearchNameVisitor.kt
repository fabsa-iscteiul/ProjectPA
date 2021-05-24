package visitor

import jsonElements.*

class SearchNameVisitor(private val nameToSearch: String): Visitor {

    val list = mutableListOf<String>()

    override fun visit(bol: JsonBoolean) {
        if(bol.name.contains(nameToSearch) && nameToSearch !="")
            list.add(bol.name)
    }

    override fun visit(ch: JsonChar) {
        if(ch.name.contains(nameToSearch) && nameToSearch !="")
            list.add(ch.name)
    }

    override fun visit(col: JsonArray) {
        if(col.name.contains(nameToSearch) && nameToSearch !="")
            list.add(col.name)
    }

    override fun visit(enum: JsonEnum) {
        if(enum.name.contains(nameToSearch)&& nameToSearch !="")
            list.add(enum.name)
    }

    override fun visit(num: JsonNumber) {
        if(num.name.contains(nameToSearch)&& nameToSearch !="")
            list.add(num.name)
    }

    override fun visit(obj: JsonObject) {
        if(obj.name.contains(nameToSearch)&& nameToSearch !="")
            list.add(obj.name)
    }

    override fun visit(str: JsonString) {
        if(str.name.contains(nameToSearch)&& nameToSearch !="")
            list.add(str.name)
    }
}