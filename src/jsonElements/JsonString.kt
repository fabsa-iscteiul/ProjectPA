package jsonElements

import visitor.Visitor

class JsonString(value:String, name:String=""): JsonElement(value,name) {

    override fun accept(v: Visitor) {
        v.visit(this)
    }

}