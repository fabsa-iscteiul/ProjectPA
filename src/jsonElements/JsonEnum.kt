package jsonElements

import Visitor

class JsonEnum(name:String, value:Enum<*>): JsonElement(name,value) {
    override fun serialize(): String {
        if(super.getObjectName() == "")
            return "${super.getValue()}"
        return "\"${super.getObjectName()}\":${super.getValue()}"
    }

    override fun accept(v: Visitor) {

    }
}