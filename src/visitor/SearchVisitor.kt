package visitor

import jsonElements.*

class SearchVisitor(private var typeToGet: String): Visitor {

    val list = mutableListOf<String>()

    override fun visit(bol: JsonBoolean) {
        if(typeToGet == "name")
            list.add(bol.name)
    }

    override fun visit(ch: JsonChar) {
        if(typeToGet == "name")
            list.add(ch.name)
    }

    override fun visit(col: JsonArray) {
        if(typeToGet == "name")
            list.add(col.name)
    }

    override fun visit(enum: JsonEnum) {
        if(typeToGet == "name")
            list.add(enum.name)
    }

    override fun visit(num: JsonNumber) {
        if(typeToGet == "name")
            list.add(num.name)
    }

    override fun visit(obj: JsonObject) {
        if(typeToGet == "name")
            list.add(obj.name)
    }

    override fun visit(str: JsonString) {
        if(typeToGet == "name")
            list.add(str.name)
    }
}