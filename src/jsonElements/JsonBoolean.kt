package jsonElements

import Visitor

class JsonBoolean(name:String,value: Boolean) : JsonElement(name,value) {

    override fun serialize(): String {
        if(super.getObjectName() == "")
            return "${super.getValue()}"
        return "\"${super.getObjectName()}\":${super.getValue()}"
    }

    override fun accept(v: Visitor) {

    }
}