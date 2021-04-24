package jsonElements

import Visitor

class JsonInteger(name: String,value: Int): JsonElement(name,value) {

    override fun serialize(): String {
        if(super.getObjectName() == "")
            return "${super.getValue()}"
        return "\"${super.getObjectName()}\":${super.getValue()}"
    }

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}