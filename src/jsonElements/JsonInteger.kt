package jsonElements

import visitor.Visitor

class JsonInteger(value: Int, name: String=""): JsonElement(value,name) {

    override fun serialize(): String {
        if(super.getObjectName() == "")
            return "${super.getValue()}"
        return "\"${super.getObjectName()}\":${super.getValue()}"
    }

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}