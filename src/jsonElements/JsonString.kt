package jsonElements

import Visitor

class JsonString(name:String, value:String): JsonElement(name,value) {

    override fun serialize(): String {
        if(super.getObjectName() == "")
            return "\"${super.getValue()}\""
        return "\"${super.getObjectName()}\":\"${super.getValue()}\"";
    }

    override fun accept(v: Visitor) {
        v.visit(this)
    }

}