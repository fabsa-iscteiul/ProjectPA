package visitor

import jsonElements.*

class SearchVisitor(private var typeToGet: String): Visitor {

    val list = mutableListOf<String>()

    override fun visit(bol: JsonBoolean) {
        if(typeToGet == "name")
            list.add(bol.getObjectName())
    }

    override fun visit(ch: JsonChar) {
        if(typeToGet == "name")
            list.add(ch.getObjectName())
    }

    override fun visit(col: JsonArray) {
        if(typeToGet == "name")
            list.add(col.getObjectName())
    }

    override fun visit(enum: JsonEnum) {
        if(typeToGet == "name")
            list.add(enum.getObjectName())
    }

    override fun visit(num: JsonNumber) {
        if(typeToGet == "name")
            list.add(num.getObjectName())
    }

    override fun visit(obj: JsonObject) {
        if(typeToGet == "name")
            list.add(obj.getObjectName())
    }

    override fun visit(str: JsonString) {
        if(typeToGet == "name")
            list.add(str.getObjectName())
    }
}