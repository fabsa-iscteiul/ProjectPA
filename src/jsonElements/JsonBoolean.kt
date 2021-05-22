package jsonElements

import visitor.Visitor

class JsonBoolean(value: Boolean, name:String="") : JsonElement(value,name) {

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}