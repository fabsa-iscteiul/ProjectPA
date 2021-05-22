package jsonElements

import visitor.Visitor

class JsonNumber(value: Number, name: String=""): JsonElement(value,name) {

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}