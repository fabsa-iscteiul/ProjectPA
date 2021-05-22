package jsonElements

import visitor.Visitor

class JsonChar(value: Char, name: String="") : JsonElement(value,name) {

    override fun accept(v: Visitor) {
        v.visit(this)
    }

}