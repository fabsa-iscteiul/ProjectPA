package jsonElements

import visitor.Visitor

class JsonNumber(value: Number, name: String=""): JsonElement(value,name) {

    override fun serialize(): String {
        if(super.name == "")
            return "${super.getValue()}"
        return "\"${super.name}\":${super.getValue()}"
    }

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}