package jsonElements

import visitor.Visitor

class JsonEnum(value:Enum<*>,name:String=""): JsonElement(value,name) {

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}