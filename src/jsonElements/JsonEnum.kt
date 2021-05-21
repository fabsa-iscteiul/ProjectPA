package jsonElements

import visitor.Visitor

class JsonEnum(value:Enum<*>,name:String=""): JsonElement(value,name) {
    override fun serialize(): String {
        if(super.name == "")
            return "${super.getValue()}"
        return "\"${super.name}\":${super.getValue()}"
    }

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}