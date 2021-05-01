package visitor

import jsonElements.*

enum class Type{
    NUMBER,
    STRING,
    BOOLEAN,
    ENUM,
    OBJECT,
    CHAR
}

class GetVisitor(private var typeToGet: Type): Visitor {

    val list = mutableListOf<JsonElement>()

    override fun visit(bol: JsonBoolean) {
        if(typeToGet == Type.BOOLEAN)
            list.add(bol)
    }

    override fun visit(ch: JsonChar) {
        if(typeToGet == Type.CHAR)
            list.add(ch)
    }

    override fun visit(col: JsonCollection) {

    }

    override fun visit(enum: JsonEnum) {
        if(typeToGet == Type.ENUM)
            list.add(enum)
    }

    override fun visit(int: JsonInteger) {
        if(typeToGet == Type.NUMBER)
            list.add(int)
    }

    override fun visit(obj: JsonObject) {
        if(typeToGet == Type.OBJECT)
            list.add(obj)
    }

    override fun visit(str: JsonString) {
        if(typeToGet == Type.STRING)
            list.add(str)
    }
}